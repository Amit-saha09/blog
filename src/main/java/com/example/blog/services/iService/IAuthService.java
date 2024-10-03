package com.example.blog.services.iService;

import com.example.blog.payload.requests.AuthRequest;
import com.example.blog.payload.requests.RegisterRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IAuthService {
    ResponseEntity<?> register(RegisterRequest request, BindingResult bindingResult); //, String captcha

    ResponseEntity<?> authenticate(AuthRequest request, BindingResult bindingResult);

}
