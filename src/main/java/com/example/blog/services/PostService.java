package com.example.blog.services;

import com.example.blog.helper.Response;
import com.example.blog.helper.messages.CommonMessageConstants;
import com.example.blog.model.Category;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.payload.requests.PostRequest;
import com.example.blog.payload.requests.PostSearchRequest;
import com.example.blog.payload.responses.PostResponse;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.ServiceRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.iService.IPostService;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class PostService extends
        BaseService<Post, PostRequest, PostResponse> implements IPostService {

    private static PostService instance;  // Singleton instance
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    // Private constructor for Singleton
    private PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Public method to get the Singleton instance
    public static synchronized PostService getInstance(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        if (instance == null) {
            instance = new PostService(postRepository, modelMapper,userRepository, categoryRepository);
        }
        return instance;
    }


    protected PostRepository getPostRepository() {
        return postRepository;
    }


    @Override
    protected ServiceRepository<Post> getServiceRepo() {
        return getPostRepository();
    }

    /**
     * This service will create Post.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<PostResponse>> createPost(PostRequest postRequest) {
        Response<PostResponse> response = new Response<>();
        try {
            User user;
            Category category;
            if (ObjectUtils.isEmpty(postRequest.getUserId())) {
                return new ResponseEntity<>(getSuccessResponse("User Not Found", response), HttpStatus.OK);
            } else {
                user = userRepository.findById(postRequest.getUserId())
                        .orElseThrow(EntityNotFoundException::new);
            }

            if (ObjectUtils.isEmpty(postRequest.getCategoryId())) {
                return new ResponseEntity<>(getSuccessResponse("Category Not Found", response), HttpStatus.OK);
            } else {
                category = categoryRepository.findById(postRequest.getCategoryId())
                        .orElseThrow(EntityNotFoundException::new);
            }
            Post post = new Post();
            post.setUser(user);
            post.setCategory(category);
            post.setComments(0);
            post.setLikes(0);
            post.setDescription(postRequest.getDescription());
            post.setIsDeleted(false);
            post.setImage(postRequest.getImage());
            Post postSave = postRepository.save(post);

            PostResponse postResponse = new PostResponse();
            modelMapper.map(postSave, postResponse);
            response.setObj(postResponse);
            return new ResponseEntity<>(getSuccessResponse(CommonMessageConstants.SAVED_EN, response), HttpStatus.OK);
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
     * This service will update Post.
     * @author Amit Saha
     * @since 25th september
     */
    @Override
    public ResponseEntity<Response<PostResponse>> updatePost(PostRequest postRequest) {
        Response<PostResponse> response = new Response();
        try{
                Post post = postRepository.findById(postRequest.getId()).orElseThrow(()-> new EntityNotFoundException("Post Not Found"));
                Post postUpdate = new Post();
                if(!ObjectUtils.isEmpty(post)){
                    post.setImage(postRequest.getImage());
                    post.setDescription(postRequest.getDescription());
                    postUpdate= postRepository.save(post);
                }
                PostResponse postResponse = new PostResponse();
                modelMapper.map(postUpdate, postResponse);
                response.setObj(postResponse);
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

    @Override
    public ResponseEntity<?> searchPost(PostSearchRequest postSearchRequest) {
        Response<Post> response = new Response();
        try{
            List<Post> postList = postRepository.findBySearch(postSearchRequest.getDescription(), postSearchRequest.getCategoryId(),
                    postSearchRequest.getUserId());
            response.setItems(postList);
            return new ResponseEntity<>(getSuccessResponse("Successful", response), HttpStatus.OK);
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
