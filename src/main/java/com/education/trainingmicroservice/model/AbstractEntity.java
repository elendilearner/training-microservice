package com.education.trainingmicroservice.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
public abstract class AbstractEntity {

    @CreationTimestamp
    private OffsetDateTime created;

    @UpdateTimestamp
    private OffsetDateTime updated;
}
