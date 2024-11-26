package com.example.blog.payload.responses;

import com.example.blog.payload.IdHolderRequestBodyDTO;
import lombok.Data;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class AboutUsResponse {

    private String content;
    private boolean aboutUsStatus;
}
