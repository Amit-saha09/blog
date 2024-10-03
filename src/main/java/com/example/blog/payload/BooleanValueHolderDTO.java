package com.example.blog.payload;

import lombok.Data;

/**
 * This class is to provide boolean value holder dto
 *
 * @author Amit Saha
 * @since 25th september 2024
 */
@Data
public class BooleanValueHolderDTO {

    private boolean success;
    private String message;
}
