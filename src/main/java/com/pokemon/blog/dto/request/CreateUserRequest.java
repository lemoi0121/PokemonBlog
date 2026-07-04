package com.pokemon.blog.dto.request;

import com.pokemon.blog.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUserRequest {

    @NotBlank(message = "Tên không được trống")
    private String name;

    @NotBlank(message = "Username không được trống")
    private String userName;

    @NotBlank(message = "Password không được trống")
    private String userPassword;

    @NotNull(message = "Role không được trống")
    private Role role;

    public CreateUserRequest() {}

    public CreateUserRequest(String name, String userName, String userPassword, Role role) {
        this.name = name;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}