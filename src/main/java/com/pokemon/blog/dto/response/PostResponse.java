package com.pokemon.blog.dto.response;

import com.pokemon.blog.entity.Post;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;      // ← userId thay vì User object
    private String userName;  // ← userName để display



    public PostResponse(Long id, String title, String content, Long userId, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
    }

    public static PostResponse fromEntity(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId(),      // ← lấy từ User object
                post.getAuthor().getUserName()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
}
