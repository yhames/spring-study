package org.example.couponcore.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Column(name = "date_created", nullable = false)
    @CreatedDate
    private LocalDateTime dateCreated;

    @Column(name = "date_updated", nullable = false)
    @LastModifiedDate
    private LocalDateTime dateUpdated;
}
