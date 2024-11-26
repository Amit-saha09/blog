package com.example.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comments extends AuditModel<Long> {

    @Column(columnDefinition = "TEXT")
    private String commentDetails;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
