package com.example.blog.services.iService;


import com.example.blog.helper.Response;
import com.example.blog.payload.requests.CategoryRequest;
import com.example.blog.payload.requests.PostRequest;
import com.example.blog.payload.requests.PostSearchRequest;
import com.example.blog.payload.responses.CategoryResponse;
import com.example.blog.payload.responses.PostResponse;
import org.springframework.http.ResponseEntity;

/**
 * This interface is for faq service
 * @author Amit Saha
 * @since 25th september
 */
public interface IPostService {

    ResponseEntity<Response<PostResponse>> createPost(PostRequest postRequest);

    ResponseEntity<Response<PostResponse>> updatePost(PostRequest postRequest);
    ResponseEntity<?> searchPost(PostSearchRequest postSearchRequest);

}
