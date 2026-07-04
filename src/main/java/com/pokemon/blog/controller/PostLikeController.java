package com.pokemon.blog.controller;

import com.pokemon.blog.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    @Autowired
    private PostLikeService postLikeService;

    @PostMapping
    public void likePost(@PathVariable Long postId) {
        postLikeService.likePost(postId);
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Long getLikeCount(@PathVariable Long postId) {
        return postLikeService.getLikeCount(postId);
    }
}