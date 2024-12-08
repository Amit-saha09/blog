package com.example.blog.payload.responses;

import com.example.blog.model.Category;
import com.example.blog.model.User;
import com.example.blog.payload.IdHolderRequestBodyDTO;
import lombok.Data;

import java.util.Date;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class PostResponse extends IdHolderRequestBodyDTO {

    private User user;

    private String title;
    private String description;

    private Category category;

    private String image;

    private Date expireDateAndTime;

    private int likes;

    private int comments;
}
