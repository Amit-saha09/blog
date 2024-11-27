/****author-->Simi****/
package com.example.blog.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    // Constructor, Getters, and Setters
    public Comment(String content) {
        this.content = content;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public BlogPost getBlogPost() { return blogPost; }
    public void setBlogPost(BlogPost blogPost) { this.blogPost = blogPost; }
}