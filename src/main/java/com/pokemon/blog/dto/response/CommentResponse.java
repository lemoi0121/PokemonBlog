package com.pokemon.blog.dto.response;

import com.pokemon.blog.entity.Comment;
import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;

    public CommentResponse(Long id, String content, Long userId, String userName, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getUserName(),
                comment.getCreatedAt()
        );
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}