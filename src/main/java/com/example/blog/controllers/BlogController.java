/*******author-->Simi*********/
/*package com.example.blog.controllers;

import com.example.blog.model.BlogPost;
import com.example.blog.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class BlogController {

    @Autowired
    private BlogPostService blogPostService;

    @PostMapping("/create")
    public BlogPost createPost(@RequestParam String title, @RequestParam String content) {
        BlogPost blogPost = new BlogPost(title, content);
        return blogPostService.initializeObservers(blogPost); // Initialize observers
    }

    @PostMapping("/{postId}/comment")
    public void addComment(@PathVariable Long postId, @RequestParam String comment) {
        BlogPost blogPost = new BlogPost("Sample Post Comment", "New Comments"); // Assuming fetching by ID
        blogPostService.addComment(blogPost, comment); // Add comment and notify observers
    }

    @PostMapping("/{postId}/publish")
    public void publishPost(@PathVariable Long postId) {
        BlogPost blogPost = new BlogPost("Sample Blog Post", "New Blog Post"); // Assuming fetching by ID
        blogPostService.publishPost(blogPost); // Publish the post and notify observers
    }
}*/
/*******author-->Simi*********/
package com.example.blog.controllers;

import com.example.blog.model.BlogPost;
import com.example.blog.model.Comment;
import com.example.blog.payload.requests.BlogPostRequest;
import com.example.blog.payload.responses.BlogPostResponse;
import com.example.blog.services.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class BlogController {

    private final BlogPostService blogPostService;

    // Create a new blog post
   /* @PostMapping("/create")
    public ResponseEntity<BlogPostResponse> createPost(@RequestBody BlogPostRequest blogPostRequest) {
        // Use DTO to create a blog post
        BlogPost blogPost = blogPostService.createBlogPost(blogPostRequest.getTitle(), blogPostRequest.getContent());

        // Simulating hardcoded response with post ID and status
        BlogPostResponse response = new BlogPostResponse(blogPost.getId(), blogPost.getTitle(), blogPost.getContent(), blogPost.isPublished());

        return ResponseEntity.ok(response);
    }*/

    // Add comment to an existing blog post (using hardcoded values)
    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> addComment(@PathVariable Long postId, @RequestParam String comment) {
        BlogPost blogPost = new BlogPost("Sample Post", "This is a sample post content"); // Hardcoded post
        blogPost.addComment(new Comment(comment)); // Add comment

        return ResponseEntity.ok("Comment added successfully");
    }

    // Publish an existing blog post (using hardcoded values)
    @PostMapping("/{postId}/publish")
    public ResponseEntity<String> publishPost(@PathVariable Long postId) {
        BlogPost blogPost = new BlogPost("Sample Post", "This is a sample post content"); // Hardcoded post
        blogPost.publish(); // Publish the post

        return ResponseEntity.ok("Blog post published successfully");
    }
}