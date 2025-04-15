package com.example.blog.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.example.blog.helper.Response;
import com.example.blog.model.User;
import com.example.blog.payload.responses.UserResponse;
import com.example.blog.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");

        userResponse = new UserResponse();
        userResponse.setEmail("test@example.com");
        userResponse.setFirstName("Test");
        userResponse.setLastName("User");
    }

    @Test
    void testGetUserProfile_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);
        ResponseEntity<?> responseEntity = userService.getUserProfile("test@example.com");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(((Response<?>) responseEntity.getBody()).isSuccess());
        assertEquals(userResponse, ((Response<?>) responseEntity.getBody()).getObj());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(modelMapper, times(1)).map(user, UserResponse.class);
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = userService.getUserProfile("test@example.com");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(((Response<?>) responseEntity.getBody()).isSuccess());
        assertEquals("Something went wrong", ((Response<?>) responseEntity.getBody()).getMessage());

        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetUserProfile_ExceptionThrown() {
        when(userRepository.findByEmail("test@example.com")).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> responseEntity = userService.getUserProfile("test@example.com");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(((Response<?>) responseEntity.getBody()).isSuccess());
        assertEquals("Database error", ((Response<?>) responseEntity.getBody()).getErrorMessage());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(logger, times(1)).error(eq("Something went wrong"), any(RuntimeException.class));
    }
}
