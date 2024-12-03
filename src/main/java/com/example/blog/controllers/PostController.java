package com.example.blog.controllers;


import com.example.blog.constatnts.PostConstant;
import com.example.blog.helper.Response;
import com.example.blog.model.Post;
import com.example.blog.payload.requests.PostRequest;
import com.example.blog.payload.requests.PostSearchRequest;
import com.example.blog.payload.responses.PostResponse;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.BaseService;
import com.example.blog.services.PostService;
import com.example.blog.services.ServiceFactory;
import com.example.blog.services.iService.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This controller is to provide all the Post relevant api's
 * @author Amit Saha
 * @since 25th september
 */
@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(PostConstant.POST)
public class PostController extends BaseController<Post, PostRequest, PostResponse> {

    private final IPostService postService;

    public PostController(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        // Fetching PostService using the updated getService method
        this.postService = (IPostService) ServiceFactory.getService(
                PostService.class,
                postRepository,
                modelMapper,
                userRepository,
                categoryRepository
        );
    }

    @Override
    protected BaseService<Post, PostRequest, PostResponse> getService() {
        return (BaseService<Post, PostRequest, PostResponse>) postService; // Cast to BaseService
    }

   /* public PostController(BaseService<Post, PostRequest, PostResponse> service, IPostService iPostService) {
        super(service);
        this.iPostService = iPostService;
    }*/


    /**
     ** This API will Create Post
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Create Post", description = "Create new Post into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Post data store into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not save Post ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PostMapping(path = PostConstant.CREATE_POST, produces = "application/json")
    public ResponseEntity<Response<PostResponse>> createPost(@RequestBody PostRequest PostRequest ){
        return postService.createPost(PostRequest);
    }

    /**
     ** This API will update Post
     * @author Amit Saha
     * @since 25th september
     */
    @Operation(summary = "Update Post", description = "Update Post into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Post data update into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not update Post ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PutMapping(path = PostConstant.UPDATE_POST, produces = "application/json")
    public ResponseEntity<Response<PostResponse>> updatePost(@RequestBody  PostRequest PostRequest){
        return postService.updatePost(PostRequest);
    }
    @Operation(summary = "Search Post", description = "Search Post into DB")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Post data update into database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400, 401", description = "Bad Request, could not update Post ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))) })
    @PostMapping(path = PostConstant.SEARCH_POST_BY_CATEGORY_ID, produces = "application/json")
    public ResponseEntity<?> search(@RequestBody PostSearchRequest postSearchRequest){
        return postService.searchPost(postSearchRequest);
    }


}
