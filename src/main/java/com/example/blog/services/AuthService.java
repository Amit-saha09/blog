package com.example.blog.services;


import com.example.blog.helper.CommonFunctions;
import com.example.blog.helper.Response;
import com.example.blog.helper.ValidationHelper;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.User;
import com.example.blog.payload.requests.AuthRequest;
import com.example.blog.payload.requests.RegisterRequest;
import com.example.blog.payload.responses.AccountResponse;
import com.example.blog.payload.responses.LoginResponse;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.iService.IAuthService;
import com.example.blog.services.iService.IJwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements IAuthService, CommonFunctions {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authManager;


    public ResponseEntity<?> register(RegisterRequest request, BindingResult bindingResult) {
        Response<AccountResponse> response = new Response<>();

        if(bindingResult.hasErrors()){
            String fieldErrors = ValidationHelper.getFieldError(bindingResult);
            response.setMessage("User not created for a validation error.");
            response.setSuccess(false);
            response.setErrorMessage(fieldErrors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Validation of email and phone
            Long count = userRepository.countByEmailAndIsDeletedFalse(request.getEmail());
            if (count > 0) {
                return new ResponseEntity<>(getErrorResponse("Email or Phone is already registered!"), HttpStatus.CONFLICT);
            }
            ModelMapper modelMapper = new ModelMapper();
            //User user = modelMapper.map(request, User.class);

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIsDeleted(false);
            user.setIsActivated(true);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());

            userRepository.save(user);

            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setEmail(user.getEmail());
            accountResponse.setUserName(user.getFirstName() + " " + user.getLastName());
            response.setObj(accountResponse);

            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
        }  catch (Exception ex) {
            ex.printStackTrace();
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> authenticate(AuthRequest request, BindingResult bindingResult) {
        Response<LoginResponse> response = new Response<>();
        if(bindingResult.hasErrors()){
            String fieldErrors = ValidationHelper.getFieldError(bindingResult);
            response.setMessage("User not authenticated for a validation error.");
            response.setSuccess(false);
            response.setErrorMessage(fieldErrors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try{

            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            if(optionalUser.isEmpty()){
                return new ResponseEntity<>(getErrorResponse("User account not found!"), HttpStatus.UNAUTHORIZED);
            }
            User user = optionalUser.get();
            /*authManager.authenticate(m
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );*/
            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                return new ResponseEntity<>(getErrorResponse("Password is incorrect"), HttpStatus.UNAUTHORIZED);
            }
            if(!user.getIsActivated()){
                return new ResponseEntity<>(getErrorResponse("This user account is not activated!"), HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = jwtService.generateToken(user);
            // Sending token in body with user other information
            LoginResponse loginResponse = new LoginResponse();


            loginResponse.setEmail(user.getEmail());
            loginResponse.setFirstName(user.getFirstName());
            loginResponse.setLastName(user.getLastName());
            loginResponse.setAccessToken(jwtToken);
            response.setObj(loginResponse);

            return new ResponseEntity<>(getSuccessResponse("Username authenticated successfully!", response), HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(getErrorResponse("Username or password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        catch (ValidationException ex) {
            return new ResponseEntity<>(getErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response.setMessage("Login failed due to internal error.");
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
