package com.example.blog.services.iService;


import com.example.blog.helper.Response;
import com.example.blog.payload.requests.AboutUsRequest;
import com.example.blog.payload.requests.CategoryRequest;
import com.example.blog.payload.responses.AboutUsResponse;
import com.example.blog.payload.responses.CategoryResponse;
import org.springframework.http.ResponseEntity;

/**
 * This interface is for faq service
 * @author Amit Saha
 * @since 25th september
 */
public interface ICategoryService {

    ResponseEntity<Response<CategoryResponse>> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<Response<CategoryResponse>> updateCategory(CategoryRequest categoryRequest);

}
