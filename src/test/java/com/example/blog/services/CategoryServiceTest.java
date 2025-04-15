package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.model.Category;
import com.example.blog.payload.requests.CategoryRequest;
import com.example.blog.payload.responses.CategoryResponse;
import com.example.blog.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCreateCategory_ReturnsCategoryResponse() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setName("Tech");
        request.setDescription("Technology posts");

        // Prepare mock entity
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName(request.getName());
        savedCategory.setDescription(request.getDescription());

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(savedCategory);

        // Act
        ResponseEntity<Response<CategoryResponse>> responseEntity = categoryService.createCategory(request);

        // Assert
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        Response<CategoryResponse> responseBody = responseEntity.getBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertTrue(responseBody.isSuccess());

        CategoryResponse categoryResponse = responseBody.getObj();
        Assertions.assertNotNull(categoryResponse);
        Assertions.assertEquals("Tech", categoryResponse.getName());
        Assertions.assertEquals("Technology posts", categoryResponse.getDescription());

        // Verify repository interaction
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }
    @Test
    public void testCreateCategory_Failure() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setName("Fail");
        request.setDescription("This should fail");

        // Simulate DB error on save
        Mockito.when(categoryRepository.save(Mockito.any(Category.class)))
                .thenThrow(new RuntimeException("Database save failed"));

        // Act
        ResponseEntity<Response<CategoryResponse>> responseEntity = categoryService.createCategory(request);
        Response<CategoryResponse> responseBody = responseEntity.getBody();

        // Assert
        Assertions.assertNotNull(responseBody);
        Assertions.assertFalse(responseBody.isSuccess()); // because the save failed
        Assertions.assertEquals("Failed to create category", responseBody.getMessage());
        Assertions.assertEquals("Database save failed", responseBody.getErrorMessage());
        Assertions.assertNull(responseBody.getObj());

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }
}