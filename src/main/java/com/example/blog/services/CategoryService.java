package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.Category;
import com.example.blog.payload.requests.CategoryRequest;
import com.example.blog.payload.responses.CategoryResponse;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.repositories.ServiceRepository;
import com.example.blog.services.iService.ICategoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


@Service
@Transactional
public class CategoryService extends
        BaseService<Category, CategoryRequest, CategoryResponse> implements ICategoryService {

    private static CategoryService instance;  // Singleton instance
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    // Private constructor for Singleton
    private CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // Public method to get the Singleton instance
    public static synchronized CategoryService getInstance(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        if (instance == null) {
            instance = new CategoryService(categoryRepository, modelMapper);
        }
        return instance;
    }


    protected CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }


    @Override
    protected ServiceRepository<Category> getServiceRepo() {
        return getCategoryRepository();
    }

    /**
     * This service will create Category.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<CategoryResponse>> createCategory(CategoryRequest categoryRequest) {
        Response<CategoryResponse> response = new Response<>();
        try {
            Long isExistsCategory = categoryRepository.countAllByNameAndIsDeletedFalse(categoryRequest.getName());
            if(isExistsCategory == 0){
                Category category = new Category();
                category.setName(categoryRequest.getName());
                category.setDescription(category.getDescription());
                category.setIsDeleted(false);
                Category categorySave = categoryRepository.save(category);

                CategoryResponse categoryResponse = new CategoryResponse();
                modelMapper.map(categorySave, categoryResponse);
                response.setObj(categoryResponse);
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
            }
            return new ResponseEntity<>(getErrorResponse(CommonMessageConstants.ALREADY_EXIST_EN), HttpStatus.CONFLICT);
        }catch(Exception ex){
            logger.error(CommonMessageConstants.NOT_SAVED_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This service will update Category.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<CategoryResponse>> updateCategory(CategoryRequest categoryRequest) {
        Response<CategoryResponse> response = new Response();
        try{
                Category category = categoryRepository.findById(categoryRequest.getId()).orElseThrow(()-> new EntityNotFoundException("Category Not Found"));
                Category categoryUpdate = new Category();
                if(!ObjectUtils.isEmpty(category)){
                    category.setName(categoryRequest.getName());
                    category.setDescription(categoryRequest.getDescription());
                    category.setIsDeleted(false);
                    categoryUpdate = categoryRepository.save(category);
                }
                CategoryResponse categoryResponse = new CategoryResponse();
                modelMapper.map(categoryUpdate, categoryResponse);
                response.setObj(categoryResponse);
                return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.UPDATED_EN, response), HttpStatus.OK);
        } catch(Exception ex){
            logger.error(CommonMessageConstants.SOMETHING_WRONG_EN, ex);
            ex.printStackTrace();
            System.out.println(ex);
            response.setMessage(CommonMessageConstants.SOMETHING_WRONG_EN);
            response.setSuccess(false);
            response.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
