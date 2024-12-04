package com.example.blog.services.iService;


import com.example.blog.helper.Response;
import com.example.blog.payload.requests.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * This interface is for User service
 * @author Amit Saha
 * @since 25th september
 */
public interface IUserService {

    ResponseEntity<?> searchableUserList(UserRequest UserRequest);

    ResponseEntity<?> banUser(String email);

}
