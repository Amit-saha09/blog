package com.example.blog.payload.requests;

import com.example.blog.constatnts.RegexConstants;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "First Name cannot be blank")
    @NotNull(message = "First Name cannot be null")
    private String firstName;
    @NotBlank(message = "Last Name cannot be blank")
    @NotNull(message = "Last Name cannot be null")
    private String lastName;
    @Email(regexp = RegexConstants.EMAIL, message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    private String email;
    @Pattern(regexp = RegexConstants.PHONE_NUMBER_BD, message = "Mobile number must be 11 digits or 13 digits and must start with 01 or +8801")
    @NotBlank(message = "Mobile number cannot be blank")
    @NotNull(message = "Mobile number cannot be null")
    private String phone;
    @Pattern(regexp = RegexConstants.PASSWORD, message = "Password must contain minimum 8 characters," +
            " contain both lower and uppercase letters," +
            " contain 1 number," +
            " contain 1 special character '-@#$%^&*()+'")
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;
    @Pattern(regexp = RegexConstants.PASSWORD, message = "Confirm password must contain minimum 8 characters," +
            " contain both lower and uppercase letters," +
            " contain 1 number," +
            " contain 1 special character '-!@ # $96^& *0+'")
    @NotBlank(message = "Confirm Password cannot be blank")
    @NotNull(message = "Confirm Password cannot be null")
    private String confirmPassword;
    @AssertTrue(message="Passwords do not match")
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
