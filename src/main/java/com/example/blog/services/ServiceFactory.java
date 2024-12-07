package com.example.blog.services;


import com.example.blog.repositories.*;
import org.modelmapper.ModelMapper;



public class ServiceFactory {


    public static Object getService(Class<?> serviceClass, Object repository, ModelMapper modelMapper) {
        if (serviceClass.equals(FaqService.class)) {
            return FaqService.getInstance((FaqRepository) repository,modelMapper);
        } else if (serviceClass.equals(AboutUsService.class)) {
            return AboutUsService.getInstance((AboutUsRepository) repository,modelMapper);
        }else if (serviceClass.equals(CategoryService.class)) {
            return CategoryService.getInstance((CategoryRepository) repository,modelMapper);
        } else if (serviceClass.equals(UserService.class)) {
            return UserService.getInstance((UserRepository) repository,modelMapper);
        }  /*else if (serviceClass.equals(PostService.class)) {
            // Properly pass the required repositories for PostService
            PostRepository postRepository = (PostRepository) repository;
            UserRepository userRepository = new UserRepository();  // Replace this with actual instance or injection
            CategoryRepository categoryRepository = new CategoryRepository(); // Replace this with actual instance or injection
            return PostService.getInstance(postRepository, modelMapper, userRepository, categoryRepository);
        }*/
        throw new IllegalArgumentException("No such service found.");
    }
    public static Object getServiceForPost(Class<?> serviceClass, PostRepository repository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        return PostService.getInstance(repository, modelMapper, userRepository, categoryRepository);
}

}
