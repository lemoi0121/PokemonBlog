package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.PostResponse;
import com.pokemon.blog.dto.request.CreatePostRequest;
import com.pokemon.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public PostResponse createPost(@Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(request);
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

    @GetMapping("/search")
    public List<PostResponse> searchPosts(@RequestParam String keyword) {
        return postService.searchPosts(keyword);
    }
}