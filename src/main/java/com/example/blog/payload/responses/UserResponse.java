package com.example.blog.payload.responses;


import com.example.blog.payload.IdHolderRequestBodyDTO;
import lombok.Data;

/**
 * This class is to provide faq response dto
 * @author Amit Saha
 * @since 25th september
 */
@Data
public class UserResponse extends IdHolderRequestBodyDTO {

    private String firstName;


    private String lastName;


    private String email;


    private String phone;

    private String userType;


    private Boolean isActivated;
}
