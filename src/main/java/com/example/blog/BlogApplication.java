package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.example.blog.services.BlogPostService;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	/**********author--> Simi******/
	// This will run after the application starts
	@Bean
	public CommandLineRunner run(BlogPostService blogPostService) {
		return args -> {
			// Simulate creating and publishing a blog post
			blogPostService.createBlogPost("New Blog Post Title", "This is the content of the new blog post.");
		};

	}
}
