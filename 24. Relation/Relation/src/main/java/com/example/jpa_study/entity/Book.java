package com.example.jpa_study.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    private Long publisherId;

    // Book에서 BookReviewInfo가져오기
    @OneToOne(mappedBy = "book") // BookReviewInfo의 연관 Key인 book_review_info_id를 table의 속성으로 갖지 않는다.
    @ToString.Exclude // ToString에서 제외
    private BookReviewInfo bookReviewInfo;
    // Entity Relationship을 사용하는 경우 ToString과 같은 것은 순환참조가 걸리게 된다.
    // 그래서 특별히 필요한 경우가 아니면 Relation은 단방향으로 걸거나 양방향으로 걸면 ToString에서 제외하는 작업을 해줘야한다.
    // 그러지 않으면 StackOverFlow오류가 발생한다.
}
