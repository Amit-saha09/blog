package com.example.blog.services.iService;


import com.example.blog.helper.Response;
import com.example.blog.payload.requests.ContactUsRequest;
import com.example.blog.payload.responses.ContactUsResponse;
import org.springframework.http.ResponseEntity;

/**
 * This interface is for faq service
 * @author Amit Saha
 * @since 25th september
 */
public interface IContactUsService {

    ResponseEntity<Response<ContactUsResponse>> createContactUs(ContactUsRequest request);

}
