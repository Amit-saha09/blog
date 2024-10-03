package com.example.blog.services.iService;


import com.example.blog.helper.Response;
import com.example.blog.payload.FaqResponse;
import com.example.blog.payload.requests.FaqRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * This interface is for faq service
 * @author Amit Saha
 * @since 25th september
 */
public interface IFaqService {

    ResponseEntity<Response<FaqResponse>> createFaq(FaqRequest faqRequest, BindingResult bindingResult);

    ResponseEntity<Response<FaqResponse>> updateFaq(FaqRequest faqRequest,BindingResult bindingResult);

}
