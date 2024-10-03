package com.example.blog.services;

import com.example.blog.repositories.FaqRepository;
import org.modelmapper.ModelMapper;

public class ServiceFactory {
    public static Object getService(Class<?> serviceClass, Object repository, ModelMapper modelMapper) {
        if (serviceClass.equals(FaqService.class)) {
            return FaqService.getInstance((FaqRepository) repository,modelMapper);
        } /*else if (serviceClass.equals(UserService.class)) {
            return UserService.getInstance((UserRepository) repository);
        } else if (serviceClass.equals(PostService.class)) {
            return PostService.getInstance((PostRepository) repository);
        }*/
        throw new IllegalArgumentException("No such service found.");
    }
}
