package com.example.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Category extends AuditModel<Long> {

    private String name;

    private String description;
}
