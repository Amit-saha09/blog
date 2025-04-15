package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.model.ContactUs;
import com.example.blog.payload.requests.ContactUsRequest;
import com.example.blog.payload.responses.ContactUsResponse;
import com.example.blog.repositories.ContactUsRepository;
import com.example.blog.repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// In-memory stub of ContactUsRepository (minimal implementation)

public class ContactUsServiceTest {

    private ContactUsService contactUsService;

    @BeforeEach
    public void setUp() {
        ContactUsRepository repository = new InMemoryContactUsRepository();
        ModelMapper modelMapper = new ModelMapper();
        contactUsService = new TestableContactUsService(repository, modelMapper);
    }

    @Test
    public void testCreateContactUsWithoutMocks() {
        // Arrange
        ContactUsRequest request = new ContactUsRequest();
        request.setEmail("test@example.com");
        request.setSubject("Help with blog");
        request.setContent("I am facing an issue with posting.");

        // Act
        ResponseEntity<Response<ContactUsResponse>> responseEntity = contactUsService.createContactUs(request);
        Response<ContactUsResponse> response = responseEntity.getBody();

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getObj());
        assertEquals("test@example.com", response.getObj().getEmail());
        assertEquals("Help with blog", response.getObj().getSubject());
        assertEquals("I am facing an issue with posting.", response.getObj().getContent());
    }
}
