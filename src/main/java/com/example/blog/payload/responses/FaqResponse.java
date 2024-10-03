package com.example.blog.payload.responses;


import com.example.blog.payload.IdHolderRequestBodyDTO;
import lombok.Data;

/**
 * This class is to provide faq response dto
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class FaqResponse extends IdHolderRequestBodyDTO {

    private String questionTitle;

    private String answerDetails;

    private boolean faqStatus;
}
