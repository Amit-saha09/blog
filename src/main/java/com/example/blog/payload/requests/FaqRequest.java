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
public class FaqRequest extends IdHolderRequestBodyDTO {

    @NotBlank(message = "Question title cannot be blank")
    @NotNull(message = "Question title cannot be null")
    private String questionTitle;

    @NotBlank(message = "Answer details cannot be blank")
    @NotNull(message = "Answer details cannot be null")
    private String answerDetails;

    private boolean faqStatus;
}
