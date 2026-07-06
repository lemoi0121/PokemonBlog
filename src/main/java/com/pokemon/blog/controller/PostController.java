package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.PostResponse;
import com.pokemon.blog.dto.response.PaginationResponse;
import com.pokemon.blog.dto.request.CreatePostRequest;
import com.pokemon.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Lấy tất cả posts (phân trang).
     *
     * @param page trang hiện tại (0-indexed), mặc định 0
     * @param size số item trên 1 trang, mặc định 10
     * @param sortBy field để sort, mặc định "id"
     * @param sortDirection ASC hoặc DESC, mặc định ASC
     * @return PaginationResponse
     */
    @GetMapping
    public PaginationResponse<PostResponse> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @Valid @RequestBody CreatePostRequest request) {
        return postService.updatePost(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Tìm kiếm posts theo keyword (phân trang).
     *
     * @param keyword từ khóa tìm kiếm
     * @param page trang hiện tại (0-indexed), mặc định 0
     * @param size số item trên 1 trang, mặc định 10
     * @return PaginationResponse
     */
    @GetMapping("/search")
    public PaginationResponse<PostResponse> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return postService.searchPosts(keyword, pageable);
    }
}