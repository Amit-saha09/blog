package com.example.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;





@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Faq extends AuditModel<Long> {


    @Column(columnDefinition = "TEXT")
    private String questionTitle;

    @Column(columnDefinition = "TEXT")
    private String answerDetails;

    private boolean faqStatus;
}
