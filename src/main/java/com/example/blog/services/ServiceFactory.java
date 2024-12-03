package com.example.blog.services;

import com.example.blog.repositories.*;
import org.modelmapper.ModelMapper;

public class ServiceFactory {

    public static Object getService(Class<?> serviceClass, Object repository, ModelMapper modelMapper,
                                    UserRepository userRepository, CategoryRepository categoryRepository) {
        if (serviceClass.equals(FaqService.class)) {
            return FaqService.getInstance((FaqRepository) repository, modelMapper);
        } else if (serviceClass.equals(AboutUsService.class)) {
            return AboutUsService.getInstance((AboutUsRepository) repository, modelMapper);
        } else if (serviceClass.equals(CategoryService.class)) {
            return CategoryService.getInstance((CategoryRepository) repository, modelMapper);
        } else if (serviceClass.equals(PostService.class)) {
            if (repository instanceof PostRepository) {
                return PostService.getInstance((PostRepository) repository, modelMapper, userRepository, categoryRepository);
            } else {
                throw new IllegalArgumentException("Invalid repository provided for PostService.");
            }
        }
        throw new IllegalArgumentException("No such service found.");
    }
}
