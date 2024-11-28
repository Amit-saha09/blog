/*******author-->Simi******/
package com.example.blog.services;

import com.example.blog.model.BlogPost;
import com.example.blog.model.User;
import com.example.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.blog.model.Comment;

@Service
//@RequiredArgsConstructor
public class BlogPostService {

    //private final UserRepository userRepository;

    // Hardcode some users as observers
    private List<User> users;

    // Constructor to initialize users
    public BlogPostService() {
        //users = List.of(new User("John"), new User("Bob"), new User("Charlie"));
    }

    // Method to create a new BlogPost
    /*public BlogPost createBlogPost(String title, String content) {
        // Create the new blog post
        BlogPost blogPost = new BlogPost(title, content);

        // Initialize observers (subscribed users)
        return initializeObservers(blogPost);
    }*/

    // Method to create a new BlogPost; simulation with notifyObservers
    public void createBlogPost(String title, String content) {
        // Create the new blog post
        BlogPost blogPost = new BlogPost(title, content);

        // Initialize observers (subscribed users)
        blogPost = initializeObservers(blogPost);
        blogPost.notifyObservers(title);
    }

    // Method to initialize observers (subscribed users)
    public BlogPost initializeObservers(BlogPost blogPost) {
        // Fetch all activated users
        /*List<User> subscribedUsers = userRepository.findByIsActivatedTrue();

        // Add each user as an observer
        for (User user : subscribedUsers) {
            blogPost.addObserver(user);
        }*/

        // Add each user as an observer (using hardcoded users)
        for (User user : users) {
            blogPost.addObserver(user);
        }

        return blogPost;
    }

    public void addComment(BlogPost blogPost, String commentContent) {
        Comment comment = new Comment(commentContent);
        comment.setBlogPost(blogPost);
        blogPost.addComment(comment); // Add comment and notify observers
    }

    public void publishPost(BlogPost blogPost) {
        blogPost.publish(); // Publish the post and notify observers
    }
}
