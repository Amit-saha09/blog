package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.model.Category;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.payload.requests.PostRequest;
import com.example.blog.payload.requests.PostSearchRequest;
import com.example.blog.payload.responses.PostResponse;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    private User testUser;
    private Category testCategory;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();

        // Create and save a test user
        testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password123");
        testUser.setIsActivated(true);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setPhone("123456789");
        userRepository.save(testUser);

        // Create and save a test category
        testCategory = new Category();
        testCategory.setName("Technology");
        categoryRepository.save(testCategory);
    }

    @Test
    public void testCreatePost_Success() {
        // Create PostRequest
        PostRequest postRequest = new PostRequest();
        postRequest.setUserEmail(testUser.getEmail());
        postRequest.setCategoryId(testCategory.getId());
        postRequest.setTitle("Sample Post");
        postRequest.setDescription("This is a sample post.");
        postRequest.setImage("sample-image.jpg");

        // Call the createPost method
        ResponseEntity<Response<PostResponse>> responseEntity = postService.createPost(postRequest);

        // Assert the response status and content
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().isSuccess());
        assertNotNull(responseEntity.getBody().getObj());
        assertEquals("Sample Post", responseEntity.getBody().getObj().getTitle());
    }

    @Test
    public void testCreatePost_UserNotFound() {
        // Create PostRequest with a non-existing user email
        PostRequest postRequest = new PostRequest();
        postRequest.setUserEmail("nonexistentuser@example.com");
        postRequest.setCategoryId(testCategory.getId());
        postRequest.setTitle("Another Post");
        postRequest.setDescription("This is another post.");
        postRequest.setImage("another-image.jpg");

        // Call the createPost method
        ResponseEntity<Response<PostResponse>> responseEntity = postService.createPost(postRequest);

        // Assert the response status and message
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("User Not Found", responseEntity.getBody().getMessage());
    }

    @Test
    public void testCreatePost_CategoryNotFound() {
        // Create PostRequest with a non-existing category ID
        PostRequest postRequest = new PostRequest();
        postRequest.setUserEmail(testUser.getEmail());
        postRequest.setCategoryId(999L);  // Invalid category ID
        postRequest.setTitle("Sample Post");
        postRequest.setDescription("This is a sample post.");
        postRequest.setImage("sample-image.jpg");

        // Call the createPost method
        ResponseEntity<Response<PostResponse>> responseEntity = postService.createPost(postRequest);

        // Assert the response status and message
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("Category Not Found", responseEntity.getBody().getMessage());
    }

    @Test
    public void testUpdatePost_Success() {
        // First, create a post
        PostRequest postRequest = new PostRequest();
        postRequest.setUserEmail(testUser.getEmail());
        postRequest.setCategoryId(testCategory.getId());
        postRequest.setTitle("Post to be updated");
        postRequest.setDescription("This post will be updated.");
        postRequest.setImage("update-image.jpg");

        ResponseEntity<Response<PostResponse>> createResponse = postService.create(postRequest);
        assertNotNull(createResponse.getBody().getObj());
        Long postId = createResponse.getBody().getObj().getId();

        // Now update the post
        postRequest.setId(postId);
        postRequest.setTitle("Updated Post Title");
        postRequest.setDescription("This is the updated description.");

        ResponseEntity<Response<PostResponse>> updateResponse = postService.updatePost(postRequest);

        // Assert the update response
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody().getObj());
        assertEquals("Updated Post Title", updateResponse.getBody().getObj().getTitle());
        assertEquals("This is the updated description.", updateResponse.getBody().getObj().getDescription());
    }

    @Test
    public void testSearchPost_Success() {
        // First, create a post
        PostRequest postRequest = new PostRequest();
        postRequest.setUserEmail(testUser.getEmail());
        postRequest.setCategoryId(testCategory.getId());
        postRequest.setTitle("Post to search");
        postRequest.setDescription("This post will be searched.");
        postRequest.setImage("search-image.jpg");

        postService.createPost(postRequest);

        // Now search for the post
        PostSearchRequest postSearchRequest = new PostSearchRequest();
        postSearchRequest.setDescription("searched");

        ResponseEntity<?> searchResponse = postService.searchPost(postSearchRequest);

        // Assert the search response
        assertEquals(HttpStatus.OK, searchResponse.getStatusCode());
        assertTrue(((Response) searchResponse.getBody()).getItems().size() > 0);
    }
}
