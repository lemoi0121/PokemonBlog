package com.pokemon.blog.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {

    @NotBlank(message = "Nội dung comment không được trống")
    private String content;

    public CreateCommentRequest() {}

    public CreateCommentRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}