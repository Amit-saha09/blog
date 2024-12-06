package com.example.blog.services;

import com.example.blog.model.ContactUs;
import com.example.blog.model.Post;
import com.example.blog.repositories.*;
import org.modelmapper.ModelMapper;

public class ServiceFactory {
    public static Object getService(Class<?> serviceClass, Object repository, ModelMapper modelMapper) {

        throw new IllegalArgumentException("No such service found.");
    }
    public static Object getServiceForPost(Class<?> serviceClass, PostRepository repository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
      return PostService.getInstance(repository, modelMapper, userRepository, categoryRepository);
    }
}
