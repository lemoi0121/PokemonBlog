package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.UserResponse;
import com.pokemon.blog.dto.request.CreateUserRequest;
import com.pokemon.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }
}