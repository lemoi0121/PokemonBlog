package com.pokemon.blog.dto.response;

public class LoginResponse {
    private String token;
    private Long userId;
    private String userName;
    private String role;

    public LoginResponse(String token, Long userId, String userName, String role) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }
}
