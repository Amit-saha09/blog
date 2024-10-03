package com.example.blog.payload;


import lombok.Data;


@Data
public class FaqResponse extends IdHolderRequestBodyDTO {

    private String questionTitle;

    private String answerDetails;


    private boolean faqStatus;
}
