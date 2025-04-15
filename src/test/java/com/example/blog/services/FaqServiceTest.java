
package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.Faq;
import com.example.blog.payload.FaqResponse;
import com.example.blog.payload.requests.FaqRequest;
import com.example.blog.repositories.FaqRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FaqServiceTest {

    private FaqRepository faqRepository;
    private ModelMapper modelMapper;
    private FaqService faqService;

    @BeforeEach
    void setUp() {
        faqRepository = mock(FaqRepository.class);
        modelMapper = new ModelMapper();
        faqService = FaqService.getInstance(faqRepository, modelMapper);
        FaqService.resetInstance(); // Reset singleton for each test
    }

    @Test
    void testCreateFaq_Success() {
        FaqRequest request = new FaqRequest();
        request.setQuestionTitle("What is Java?");
        request.setAnswerDetails("Java is a programming language.");
        request.setFaqStatus(true);

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");

        when(faqRepository.countAllByIsDeletedFalseAndQuestionTitle(request.getQuestionTitle())).thenReturn(0L);
        when(faqRepository.save(any(Faq.class))).thenAnswer(invocation -> {
            Faq faq = invocation.getArgument(0);
            faq.setId(1L);
            return faq;
        });

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.createFaq(request, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().getSuccess());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("What is Java?", responseEntity.getBody().getObj().getQuestionTitle());
    }

    @Test
    void testCreateFaq_DuplicateTitle() {
        FaqRequest request = new FaqRequest();
        request.setQuestionTitle("Duplicate?");
        request.setAnswerDetails("Already exists.");
        request.setFaqStatus(true);

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");

        when(faqRepository.countAllByIsDeletedFalseAndQuestionTitle(request.getQuestionTitle())).thenReturn(1L);

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.createFaq(request, bindingResult);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals(CommonMessageConstants.FAQ_QUESTION_TITLE_ALREADY_EXIST, responseEntity.getBody().getMessage());
    }

    @Test
    void testCreateFaq_ValidationError() {
        FaqRequest request = new FaqRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");
        bindingResult.rejectValue("questionTitle", "NotEmpty", "Question title is required");

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.createFaq(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertTrue(responseEntity.getBody().getErrorMessage().contains("Question title"));
    }

    @Test
    void testUpdateFaq_Success() {
        FaqRequest request = new FaqRequest();
        request.setId(1L);
        request.setQuestionTitle("Updated");
        request.setAnswerDetails("Updated answer.");
        request.setFaqStatus(true);

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");

        Faq existing = new Faq();
        existing.setId(1L);
        existing.setQuestionTitle("Old");

        when(faqRepository.countAllByIsDeletedFalseAndQuestionTitleAndIdNot(request.getQuestionTitle(), request.getId()))
                .thenReturn(0L);
        when(faqRepository.findById(request.getId())).thenReturn(java.util.Optional.of(existing));
        when(faqRepository.save(any(Faq.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.updateFaq(request, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("Updated", responseEntity.getBody().getObj().getQuestionTitle());
    }

    @Test
    void testUpdateFaq_NotFoundDueToConflict() {
        FaqRequest request = new FaqRequest();
        request.setId(1L);
        request.setQuestionTitle("Conflict");
        request.setAnswerDetails("Conflict content");

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");

        when(faqRepository.countAllByIsDeletedFalseAndQuestionTitleAndIdNot(request.getQuestionTitle(), request.getId()))
                .thenReturn(1L);

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.updateFaq(request, bindingResult);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals(CommonMessageConstants.NOT_FOUND_EN, responseEntity.getBody().getMessage());
    }

    @Test
    void testUpdateFaq_ValidationError() {
        FaqRequest request = new FaqRequest();
        request.setId(1L);
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");
        bindingResult.rejectValue("questionTitle", "NotEmpty", "Required");

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.updateFaq(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
    }

    @Test
    void testUpdateFaq_EntityNotFoundException() {
        FaqRequest request = new FaqRequest();
        request.setId(99L);
        request.setQuestionTitle("Doesn't Exist");
        request.setAnswerDetails("Nothing");

        BindingResult bindingResult = new BeanPropertyBindingResult(request, "faqRequest");

        when(faqRepository.countAllByIsDeletedFalseAndQuestionTitleAndIdNot(request.getQuestionTitle(), request.getId()))
                .thenReturn(0L);
        when(faqRepository.findById(request.getId())).thenThrow(new EntityNotFoundException("Faq Not Found"));

        ResponseEntity<Response<FaqResponse>> responseEntity = faqService.updateFaq(request, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertTrue(responseEntity.getBody().getErrorMessage().contains("Faq Not Found"));
    }
}
