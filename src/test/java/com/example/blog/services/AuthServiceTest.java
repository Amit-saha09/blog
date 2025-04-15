/*
package com.example.blog.services;

import com.example.blog.payload.requests.AuthRequest;
import com.example.blog.payload.responses.AccountResponse;
import com.example.blog.payload.responses.LoginResponse;
import com.example.blog.payload.requests.RegisterRequest;
//import com.example.blog.model;
import com.example.blog.model.User;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.iService.IJwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private IJwtService jwtService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        jwtService = new DummyJwtService(); // <-- using dummy implementation
        authService = new AuthService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setUsername("testuser");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("dummy-token", response.getToken());
    }

    @Test
    void testRegister_DuplicateEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertEquals("User already exists with this email", exception.getMessage());
    }

    @Test
    void testAuthenticate_Success() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        AuthResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("dummy-token", response.getToken());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongPassword");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(request);
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }
}
*/
