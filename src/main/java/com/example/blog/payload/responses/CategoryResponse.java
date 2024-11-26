package com.example.blog.payload.responses;

import lombok.Data;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class CategoryResponse {

    private Long id;
    private String name;

    private String description;
}
