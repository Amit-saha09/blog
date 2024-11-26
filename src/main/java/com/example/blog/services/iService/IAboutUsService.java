package com.example.blog.services.iService;


import com.example.blog.helper.Response;
import com.example.blog.payload.requests.AboutUsRequest;
import com.example.blog.payload.responses.AboutUsResponse;
import org.springframework.http.ResponseEntity;

/**
 * This interface is for faq service
 * @author Amit Saha
 * @since 25th september
 */
public interface IAboutUsService {

    ResponseEntity<Response<AboutUsResponse>> createAboutUs(AboutUsRequest aboutUsRequest);

    ResponseEntity<Response<AboutUsResponse>> updateAboutUs(AboutUsRequest aboutUsRequest);

}
