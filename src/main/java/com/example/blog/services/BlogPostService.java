/*******author-->Simi******/
package com.example.blog.services;

import com.example.blog.model.BlogPost;
import com.example.blog.model.User;
import com.example.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.blog.model.Comment;

@Service
//@RequiredArgsConstructor
public class BlogPostService {

    List<User> subscribedUsers;

    private final UserRepository userRepository;  // Will be injected by Spring

    @Autowired  // Ensure this annotation is here
    public BlogPostService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to create a new BlogPost; simulation with notifyObservers
    public BlogPost createBlogPost(String title, String content) {
        // Create the new blog post
        BlogPost blogPost = new BlogPost(title, content);

        // Log the creation of the new post to the console (for debugging purposes)
        System.out.println("A new blog post has been created: " + title);

        // Initialize observers (subscribed users)
        blogPost = initializeObservers(blogPost);
        blogPost.notifyObservers(title);
        return blogPost;
    }

    // Method to initialize observers (subscribed users)
    public BlogPost initializeObservers(BlogPost blogPost) {

        // Fetch all activated users
        subscribedUsers = userRepository.findByIsActivatedTrue();
        System.out.println("List of subscribed users" + subscribedUsers);

        // Add each user as an observer
        for (User user : subscribedUsers) {
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
