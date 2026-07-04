package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.UserResponse;
import com.pokemon.blog.dto.request.CreateUserRequest;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUser(Long id);

    UserResponse createUser(CreateUserRequest request);
}