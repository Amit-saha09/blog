package com.example.blog.payload.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String accessToken;
    private String userType;
}
