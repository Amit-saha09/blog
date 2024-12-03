package com.example.blog.controllers;


import com.example.blog.constatnts.CategoryConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.Category;
import com.example.blog.payload.requests.CategoryRequest;
import com.example.blog.payload.responses.CategoryResponse;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.services.BaseService;
import com.example.blog.services.CategoryService;
import com.example.blog.services.ServiceFactory;
import com.example.blog.services.iService.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This controller is to provide all the Category relevant api's
 * @author Amit Saha
 * @since 25th september
 */
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(CategoryConstant.CATEGORY)
public class CategoryController extends BaseController<Category, CategoryRequest, CategoryResponse> {

    private final ICategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryService = (ICategoryService) ServiceFactory.getService(
                CategoryService.class,
                categoryRepository,
                modelMapper,
                null,  // UserRepository not needed for CategoryService
                null   // CategoryRepository not needed for CategoryService
        );
    }


    @Override
    protected BaseService<Category, CategoryRequest, CategoryResponse> getService() {
        return (BaseService<Category, CategoryRequest, CategoryResponse>) categoryService; // Cast to BaseService
    }

   /* public CategoryController(BaseService<Category, CategoryRequest, CategoryResponse> service, ICategoryService iCategoryService) {
        super(service);
        this.iCategoryService = iCategoryService;
    }*/


    /**
     ** This API will Create Category
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Create Category", description = "Create new Category into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category data store into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not save Category ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PostMapping(path = CategoryConstant.CREATE_CATEGORY, produces = "application/json")
    public ResponseEntity<Response<CategoryResponse>> createCategory(@RequestBody  CategoryRequest categoryRequest ){
        return categoryService.createCategory(categoryRequest);
    }

    /**
     ** This API will update Category
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Update Category", description = "Update Category into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category data update into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not update Category ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PutMapping(path = CategoryConstant.UPDATE_CATEGORY, produces = "application/json")
    public ResponseEntity<Response<CategoryResponse>> updateCategory(@RequestBody  CategoryRequest categoryRequest){
        return categoryService.updateCategory(categoryRequest);
    }


}
