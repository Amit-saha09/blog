package com.example.blog.controllers;


import com.example.blog.constatnts.UserConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.User;

import com.example.blog.payload.requests.UserRequest;
import com.example.blog.payload.responses.UserResponse;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.BaseService;

import com.example.blog.services.ServiceFactory;

import com.example.blog.services.UserService;
import com.example.blog.services.iService.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * This controller is to provide all the User relevant api's
 * @author Amit Saha
 * @since 25th september
 */
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(UserConstant.USER)
public class UserController extends BaseController<User, UserRequest, UserResponse> {

    private final IUserService userService;

    public UserController(UserRepository UserRepository, ModelMapper modelMapper) {
        this.userService = (IUserService) ServiceFactory.getService(UserService.class, UserRepository, modelMapper);
    }

    @Override
    protected BaseService<User, UserRequest, UserResponse> getService() {
        return (BaseService<User, UserRequest, UserResponse>) userService; // Cast to BaseService
    }

   /* public UserController(BaseService<User, UserRequest, UserResponse> service, IUserService iUserService) {
        super(service);
        this.iUserService = iUserService;
    }*/


    /**
     ** This API will Create User
     * @author Amit Saha
     * @since 25th september
     */

    @PostMapping(path = UserConstant.SEARCH_USER, produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest ){
        return userService.searchableUserList(userRequest);
    }

    /**
     ** This API will update User
     * @author Amit Saha
     * @since 25th september
     */

    @PutMapping(path = UserConstant.BAN_USER, produces = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest){
        return userService.banUser(userRequest.getEmail());
    }


}
