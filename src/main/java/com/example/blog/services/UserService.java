package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.ValidationHelper;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.User;

import com.example.blog.payload.requests.UserRequest;
import com.example.blog.payload.responses.UserResponse;
import com.example.blog.repositories.UserRepository;
import com.example.blog.repositories.ServiceRepository;
import com.example.blog.services.iService.IUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class UserService extends
        BaseService<User, UserRequest, UserResponse> implements IUserService {

    private static UserService instance;  // Singleton instance
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Private constructor for Singleton
    private UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Public method to get the Singleton instance
    public static synchronized UserService getInstance(UserRepository userRepository, ModelMapper modelMapper) {
        if (instance == null) {
            instance = new UserService(userRepository, modelMapper);
        }
        return instance;

    }

    // Method to destroy the current Singleton instance
    public static synchronized void resetInstance()
    {
        instance = null;
    }


    protected UserRepository getUserRepository() {
        return userRepository;
    }


    @Override
    protected ServiceRepository<User> getServiceRepo() {
        return getUserRepository();
    }

    /**
     * This service will create User.
     * @author Amit Saha
     * @since 25th september
     */

    @Override
    public ResponseEntity<?> searchableUserList(UserRequest request) {
        Response<User> response = new Response<>();
        try {
            List<User> users = userRepository.findBySearch(request.getEmail(),request.getPhone());
            response.setItems(users);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.DATA_FOUND_EN, response), HttpStatus.OK);

        }catch(Exception ex){
            logger.error(CommonMessageConstants.NO_DATA_FOUND_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This service will update User.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<?> banUser(String email) {
        Response<UserResponse> response = new Response();
        try{

            User user = userRepository.findByEmail(email).orElseThrow();
            user.setIsActivated(false);
            userRepository.save(user);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN), HttpStatus.OK);
        } catch(Exception ex){
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getUserProfile(String email) {
        Response<UserResponse> response = new Response();
        try{

            User user = userRepository.findByEmail(email).orElseThrow();
            UserResponse userResponse =modelMapper.map(user, UserResponse.class);
            response.setObj(userResponse);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.DATA_FOUND_EN,response), HttpStatus.OK);
        } catch(Exception ex){
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateUserProfile(UserRequest request) {
        Response<UserResponse> response = new Response();
        try{

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhone(request.getPhone());
           /* if(request.getWantsPasswordChange()){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }*/
            userRepository.save(user);
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            response.setObj(userResponse);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.DATA_FOUND_EN,response), HttpStatus.OK);
        } catch(Exception ex){
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
