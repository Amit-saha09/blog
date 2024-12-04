package com.example.blog.payload.requests;

import com.example.blog.payload.IdHolderRequestBodyDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * This class is to provide faq request
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class UserRequest extends IdHolderRequestBodyDTO {


    private String firstName;


    private String lastName;


    private String email;


    private String phone;

    private String userType;


    private Boolean isActivated;



}
