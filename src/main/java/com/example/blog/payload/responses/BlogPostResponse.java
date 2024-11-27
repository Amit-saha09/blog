/*****author-->Simi*****/
package com.example.blog.payload.responses;

public class BlogPostResponse {

    private Long id;
    private String title;
    private String content;
    private boolean isPublished;

    // Constructor for response
    public BlogPostResponse(Long id, String title, String content, boolean isPublished) {
        this.id = id;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }
}
