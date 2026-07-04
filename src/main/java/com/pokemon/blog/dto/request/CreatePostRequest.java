package com.pokemon.blog.dto.request;

import com.pokemon.blog.config.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {

    @NotBlank(message = AppConstants.VALIDATION_TITLE_REQUIRED)
    private String title;

    @NotBlank(message = AppConstants.VALIDATION_CONTENT_REQUIRED)
    @Size(min = AppConstants.POST_CONTENT_MIN_LENGTH,
            message = AppConstants.VALIDATION_CONTENT_MIN_LENGTH)
    private String content;

    public CreatePostRequest() {}

    public CreatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
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
}