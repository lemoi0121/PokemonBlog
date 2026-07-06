package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.UserResponse;
import com.pokemon.blog.dto.response.PaginationResponse;
import com.pokemon.blog.dto.request.CreateUserRequest;
import com.pokemon.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Lấy tất cả users (phân trang).
     *
     * @param page trang hiện tại (0-indexed), mặc định 0
     * @param size số item trên 1 trang, mặc định 10
     * @param sortBy field để sort, mặc định "id"
     * @param sortDirection ASC hoặc DESC, mặc định ASC
     * @return PaginationResponse
     */
    @GetMapping
    public PaginationResponse<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}