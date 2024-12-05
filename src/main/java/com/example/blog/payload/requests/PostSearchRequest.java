package com.example.blog.payload.requests;

import com.example.blog.payload.IdHolderRequestBodyDTO;
import lombok.Data;

import java.util.Date;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class PostSearchRequest extends IdHolderRequestBodyDTO {

    private String userEmail;

    private String description;

    private Long categoryId;
}
