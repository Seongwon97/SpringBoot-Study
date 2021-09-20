package com.example.jpa_study.entity;

import com.example.jpa_study.entity.listener.Auditable;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data //CreatedAt,UpdatedAt은 Getter,Setter가 필요하여 추가
@MappedSuperclass // 해당 class의 Field를 상속받는 entity의 column으로 포함시켜 주는 Annotation
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity  implements Auditable {
    @CreatedDate
    private LocalDateTime  createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
