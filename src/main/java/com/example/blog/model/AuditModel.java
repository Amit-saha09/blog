package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * This abstract class is to provide for audit model
 *
 * @author Amit Saha
 * @since 25th september
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"id", "createdAt","updatedAt", "isDeleted"},
        allowGetters = true
)
@Data
public abstract class AuditModel<U> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @CreatedBy
    @Column(name="created_by")
    private U createdBy;

    @LastModifiedBy
    @Column(name="updated_by")
    private U updatedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
