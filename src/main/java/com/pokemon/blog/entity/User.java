package com.pokemon.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ten nguoi dung khong duoc rong")
    private String name;

    @NotBlank(message = "Ten dang nhap khong duoc rong")
    private String userName;

    @NotBlank(message = "Mat khau khong duoc rong")
    private String userPassword;

    @NotNull(message = "Role khong duoc rong")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){}

    public User(Long id, String name, String userName, String userPassword, Role role) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
