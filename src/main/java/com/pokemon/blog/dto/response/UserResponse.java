package com.pokemon.blog.dto.response;

import com.pokemon.blog.entity.Role;
import com.pokemon.blog.entity.User;

public class UserResponse {
    private Long id;
    private String name;
    private String userName;
    private Role role;

    public UserResponse(Long id, String name, String userName, Role role) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.role = role;
    }

    public static UserResponse fromEntity(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getRole()
        );
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }


    public Role getRole() {
        return role;
    }
}
