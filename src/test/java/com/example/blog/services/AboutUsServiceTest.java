package com.example.blog.services;
import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.AboutUs;
import com.example.blog.payload.requests.AboutUsRequest;
import com.example.blog.payload.responses.AboutUsResponse;
import com.example.blog.repositories.AboutUsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AboutUsServiceTest {



    private AboutUsRepository aboutUsRepository;
    private ModelMapper modelMapper;
    private AboutUsService aboutUsService;

    @BeforeEach
    void setUp() {
        aboutUsRepository = mock(AboutUsRepository.class);
        modelMapper = new ModelMapper();
        aboutUsService = AboutUsService.getInstance(aboutUsRepository, modelMapper);
    }

    @Test
    void testCreateAboutUsSuccess() {
        AboutUsRequest request = new AboutUsRequest();
        request.setAboutUsStatus(true);
        request.setContent("About us content");

        when(aboutUsRepository.countAllByAboutUsStatusTrue()).thenReturn(0L);

        AboutUs savedAboutUs = new AboutUs();
        savedAboutUs.setId(1L);
        savedAboutUs.setAboutUsStatus(true);
        savedAboutUs.setContent("About us content");
        savedAboutUs.setIsDeleted(false);

        when(aboutUsRepository.save(any(AboutUs.class))).thenReturn(savedAboutUs);

        ResponseEntity<Response<AboutUsResponse>> responseEntity = aboutUsService.createAboutUs(request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody().getObj());
        assertEquals("About us content", responseEntity.getBody().getObj().getContent());
        assertTrue(responseEntity.getBody().isSuccess());
    }

    @Test
    void testCreateAboutUsAlreadyExists() {
        AboutUsRequest request = new AboutUsRequest();
        request.setAboutUsStatus(true);
        request.setContent("Content");

        when(aboutUsRepository.countAllByAboutUsStatusTrue()).thenReturn(1L);

        ResponseEntity<Response<AboutUsResponse>> responseEntity = aboutUsService.createAboutUs(request);

        assertEquals(409, responseEntity.getStatusCodeValue());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals(CommonMessageConstants.ALREADY_EXIST_EN, responseEntity.getBody().getMessage());
    }

    @Test
    void testCreateAboutUsException() {
        AboutUsRequest request = new AboutUsRequest();
        request.setAboutUsStatus(true);
        request.setContent("Crash");

        when(aboutUsRepository.countAllByAboutUsStatusTrue()).thenThrow(new RuntimeException("DB failure"));

        ResponseEntity<Response<AboutUsResponse>> responseEntity = aboutUsService.createAboutUs(request);

        assertEquals(500, responseEntity.getStatusCodeValue());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("DB failure", responseEntity.getBody().getErrorMessage());
    }

    @Test
    void testUpdateAboutUsSuccess() {
        AboutUsRequest request = new AboutUsRequest();
        request.setId(1L);
        request.setContent("Updated content");
        request.setAboutUsStatus(true);

        AboutUs existing = new AboutUs();
        existing.setId(1L);
        existing.setContent("Old content");
        existing.setAboutUsStatus(false);
        existing.setIsDeleted(false);

        when(aboutUsRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(aboutUsRepository.save(existing)).thenReturn(existing);

        ResponseEntity<Response<AboutUsResponse>> responseEntity = aboutUsService.updateAboutUs(request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Updated content", responseEntity.getBody().getObj().getContent());
        assertTrue(responseEntity.getBody().isSuccess());
    }

    @Test
    void testUpdateAboutUsEntityNotFound() {
        AboutUsRequest request = new AboutUsRequest();
        request.setId(99L);

        when(aboutUsRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Response<AboutUsResponse>> responseEntity = aboutUsService.updateAboutUs(request);

        assertEquals(500, responseEntity.getStatusCodeValue());
        assertFalse(responseEntity.getBody().isSuccess());
        assertTrue(responseEntity.getBody().getErrorMessage().contains("AboutUs Not Found"));
    }

    @Test
    void testUpdateAboutUsException() {
        AboutUsRequest request = new AboutUsRequest();
        request.setId(1L);

        when(aboutUsRepository.findById(1L)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<Response<AboutUsResponse>> responseEntity = aboutUsService.updateAboutUs(request);

        assertEquals(500, responseEntity.getStatusCodeValue());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("Unexpected error", responseEntity.getBody().getErrorMessage());
    }
}
