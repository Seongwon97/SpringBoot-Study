// 가장 기본이되는 Rest Templete
package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {


    // http://localhost/api/server/hello 로 요청해서 response를 받아오기
    public UserResponse hello(){
        // uri 주소 생성
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090") //http://localhost에 호출
                .path("/api/server/hello")
                .queryParam("name", "steve")  // query parameter가 필요한 경우 이와 같이 사용
                .queryParam("age", 10)
                .encode()
                .build()
                .toUri();

        System.out.println(uri.toString());

        RestTemplate restTemplete = new RestTemplate();
//        String result = restTemplete.getForObject(uri, String.class); // 문자열로  Uri를 받는다.
        // getForObject는 Get Request를 uri로 보내서 결과를 두번째 parameter로 적은 Object타입으로 받아온다.

        // getForEntity로 하면 결과를 Entity로 받아 http status와 data등 여러 데이터 확인이 가능하다.
//        ResponseEntity<String> result = restTemplete.getForEntity(uri, String.class);



        // Json 타입으로 데이터를 받기 위해서는 DTO를 사용
        // 받는 데이터 타입을 DTO인 userResponse.class로 변경
        ResponseEntity<UserResponse> result = restTemplete.getForEntity(uri, UserResponse.class);
        // entity로 데이터를 가져오겠다(Get)~~
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }

    public UserResponse post() {
        // http://localhost:9090/api/server/user/{userId}/name/{userName} 로 post하기

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve") // {userId}, {userName}에 들어갈 값을 순차적으로 입력
                .toUri();

        System.out.println(uri);

        // object를 넣어주면 object mapper가 json으로 바꿔주고
        // rest template에서 http body에 json을 넣어줄 것이다.
        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponse.class);
        // uri에 req object를 보내서 응답은 UserResponse.class타입으로 받을 것이다!!
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }


    // 실제 실무에서 사용을 할때는 위와 다르게 Header값을 추가하여 사용한다
    // Header를 추가하여 사용하는 예시를 알아보자
    public UserResponse exchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve") // {userId}, {userName}에 들어갈 값을 순차적으로 입력
                .toUri();

        System.out.println(uri);

        // object를 넣어주면 object mapper가 json으로 바꿔주고
        // rest template에서 http body에 json을 넣어줄 것이다.
        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        RequestEntity<UserRequest> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity, UserResponse.class);
        // RequestEntity객체와 반환 type을 적어서 출력

        return response.getBody();
    }


    public Req<UserResponse> genericExchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}/post2")
                .encode()
                .build()
                .expand(100, "steve") // {userId}, {userName}에 들어갈 값을 순차적으로 입력
                .toUri();

        System.out.println(uri);


        UserRequest userRequest = new UserRequest();
        userRequest.setName("steve");
        userRequest.setAge(10);

        Req req = new Req<UserRequest>();
        req.setHeader(new Req.Header());
        req.setResBody(userRequest);



        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Req<UserResponse>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>(){});
        // Response type으로 Req<UserResponse>.class을 쓰고 싶은데 Generic에는 class를 붙일 수 없다.
        // 그래서 new ParameterizedTypeReference<Req<UserResponse>>(){}를 사용한다. <>안에 type은 앞에서 지정했기에 생략해도 괜찮다

        return response.getBody();
    }
}
