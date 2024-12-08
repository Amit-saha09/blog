package com.example.blog.payload.requests;

import com.example.blog.payload.IdHolderRequestBodyDTO;
import lombok.Data;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class ContactUsRequest extends IdHolderRequestBodyDTO {


    private String subject;


    private String content;

    private String email;

    private boolean reviewed;
}
