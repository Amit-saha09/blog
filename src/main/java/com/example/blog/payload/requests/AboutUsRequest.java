package com.example.blog.payload.requests;

import com.example.blog.payload.IdHolderRequestBodyDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class AboutUsRequest extends IdHolderRequestBodyDTO {

    private String content;
    private boolean aboutUsStatus;
}
