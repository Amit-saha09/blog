package com.example.blog.payload.requests;

import com.example.blog.model.Category;
import com.example.blog.model.User;
import com.example.blog.payload.IdHolderRequestBodyDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class PostRequest extends IdHolderRequestBodyDTO {

    private String userEmail;
    private String title;

    private String description;

    private Long categoryId;

    private String image;

    private Date expireDateAndTime;

    private int likes;

    private int comments;
}
