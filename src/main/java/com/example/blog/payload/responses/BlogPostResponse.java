/*****author-->Simi*****/
package com.example.blog.payload.responses;

public class BlogPostResponse {

    private Long id;
    private String title;
    private String description;
    private boolean isPublished;

    // Constructor for response
    public BlogPostResponse(Long id, String title, String description, boolean isPublished) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublished = isPublished;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }
}
