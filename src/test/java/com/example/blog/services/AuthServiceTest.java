package com.example.blog.services;

import com.example.blog.helper.Response;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;
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

    // Successful registration
    @Test
    void testRegister_Successful() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userRepository.countByEmailAndIsDeletedFalse("john@example.com")).thenReturn(0L);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            return user;
        });

        ResponseEntity<?> responseEntity = authService.register(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<AccountResponse> body = (Response<AccountResponse>) responseEntity.getBody();

        assertNotNull(body);
        assertTrue(body.isSuccess());
        assertNotNull(body.getObj());
        assertEquals("john@example.com", body.getObj().getEmail());
        assertEquals("John Doe", body.getObj().getUserName());
    }
    // Duplicate email registration
    @Test
    void testRegister_DuplicateEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userRepository.countByEmailAndIsDeletedFalse("john@example.com")).thenReturn(1L);

        ResponseEntity<?> responseEntity = authService.register(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<?> responseBody = (Response<?>) responseEntity.getBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("Email or Phone is already registered!", responseBody.getMessage());
    }

    // Validation error (e.g., invalid email)
    @Test
    void testRegister_ValidationError() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("");
        request.setLastName("Doe");
        request.setEmail("invalid-email");
        request.setPhone("1234567890");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("registerRequest", "email", "Invalid email format"),
                new FieldError("registerRequest", "firstName", "First name is required")
        ));

        ResponseEntity<?> responseEntity = authService.register(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<?> responseBody = (Response<?>) responseEntity.getBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("User not created for a validation error.", responseBody.getMessage());
        assertTrue(responseBody.getErrorMessage().contains("Invalid email format"));
        assertTrue(responseBody.getErrorMessage().contains("First name is required"));
    }


    // Password length check
    @Test
    void testRegister_PasswordLength() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("123"); // Invalid password, too short

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("registerRequest", "password", "Password length should be at least 6 characters.")
        ));

        ResponseEntity<?> responseEntity = authService.register(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<?> responseBody = (Response<?>) responseEntity.getBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("User not created for a validation error.", responseBody.getMessage());
        assertTrue(responseBody.getErrorMessage().contains("Password length should be at least 6 characters."));
    }

    // Internal server error
    @Test
    void testRegister_InternalServerError() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userRepository.countByEmailAndIsDeletedFalse("john@example.com")).thenReturn(0L);
        when(passwordEncoder.encode("password")).thenThrow(new RuntimeException("Database issue"));

        ResponseEntity<?> responseEntity = authService.register(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<?> responseBody = (Response<?>) responseEntity.getBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("Something went wrong. Please try again later.", responseBody.getMessage());
    }

    // Successful authentication
    @Test
    void testAuthenticate_Successful() {
        AuthRequest request = new AuthRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encoded-password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setIsActivated(true);
        user.setUserType("USER");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encoded-password")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("dummy-token");

        ResponseEntity<?> responseEntity = authService.authenticate(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<LoginResponse> responseBody = (Response<LoginResponse>) responseEntity.getBody();

        assertNotNull(responseBody);
        assertTrue(responseBody.isSuccess());
        assertEquals("Username authenticated successfully!", responseBody.getMessage());

        LoginResponse loginResponse = responseBody.getObj();
        assertNotNull(loginResponse);
        assertEquals("john@example.com", loginResponse.getEmail());
        assertEquals("John", loginResponse.getFirstName());
        assertEquals("Doe", loginResponse.getLastName());
        assertEquals("USER", loginResponse.getUserType());
        assertEquals("dummy-token", loginResponse.getAccessToken());
    }

    // Invalid email during authentication
    @Test
    void testAuthenticate_InvalidEmail() {
        AuthRequest request = new AuthRequest();
        request.setEmail("invalid@example.com");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = authService.authenticate(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Response<?> responseBody = (Response<?>) responseEntity.getBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("User account not found!", responseBody.getMessage());
    }


    // Wrong password during authentication
    @Test
    void testAuthenticate_WrongPassword() {
        AuthRequest request = new AuthRequest();
        request.setEmail("john@example.com");
        request.setPassword("wrong-password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encoded-password");
        user.setIsActivated(true);

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "encoded-password")).thenReturn(false);

        ResponseEntity<?> responseEntity = authService.authenticate(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        Response<?> responseBody = (Response<?>) responseEntity.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("Password is incorrect", responseBody.getMessage());
    }


    // Account not activated during authentication
    @Test
    void testAuthenticate_AccountNotActivated() {
        AuthRequest request = new AuthRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encoded-password");
        user.setIsActivated(false);  // Account is not activated

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encoded-password")).thenReturn(true);

        ResponseEntity<?> responseEntity = authService.authenticate(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        Response<?> responseBody = (Response<?>) responseEntity.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("This user account is not activated!", responseBody.getMessage());
    }

    // Internal server error during authentication
    @Test
    void testAuthenticate_InternalServerError() {
        AuthRequest request = new AuthRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userRepository.findByEmail("john@example.com")).thenThrow(new RuntimeException("Database issue"));

        ResponseEntity<?> responseEntity = authService.authenticate(request, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        Response<?> responseBody = (Response<?>) responseEntity.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isSuccess());
        assertEquals("Login failed due to internal error.", responseBody.getMessage());
        assertEquals("Database issue", responseBody.getErrorMessage());
    }

}
