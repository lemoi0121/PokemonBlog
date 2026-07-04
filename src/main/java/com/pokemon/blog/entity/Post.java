package com.pokemon.blog.entity;

import com.pokemon.blog.config.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = AppConstants.VALIDATION_TITLE_REQUIRED)
    private String title;

    @NotBlank(message = AppConstants.VALIDATION_CONTENT_REQUIRED)
    @Size(min = AppConstants.POST_CONTENT_MIN_LENGTH,
            message = AppConstants.VALIDATION_CONTENT_MIN_LENGTH)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;


    public Post() {}
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post(Long id, String title, String content, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
