package com.example.blog.controllers;


import com.example.blog.constatnts.AuthConstant;
import com.example.blog.helper.Response;
import com.example.blog.payload.requests.AuthRequest;
import com.example.blog.payload.requests.RegisterRequest;
import com.example.blog.services.iService.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(AuthConstant.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
/**
     *This API will register users .
     * @author Amit Saha
     * @since 25th september
     */

    @Operation(summary = "Register applicant", description = "Register the users with 'Applicant' role")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User registered successfully ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AuthController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Email or phone is already registered ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class))))})
    @PostMapping(path = AuthConstant.REGISTRATION, produces = "application/json")
    public ResponseEntity<?> registerApplicant(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) {
        return authService.register(request, bindingResult);
    }




    /**
     *This API will register users .
     * @author Amit Saha
     * @since 25th september
     */

    @Operation(summary = "Aunthenticate users", description = "Authenticate the users trying to access the api endpoint")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AuthController.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "User not created for internal error ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class))))})
    @PostMapping(path = AuthConstant.AUTHENTICATE, produces = "application/json")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthRequest request, BindingResult bindingResult) {
        return authService.authenticate(request, bindingResult);
    }



}




