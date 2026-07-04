package com.pokemon.blog.controller;

import com.pokemon.blog.dto.response.CommentResponse;
import com.pokemon.blog.dto.request.CreateCommentRequest;
import com.pokemon.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @PostMapping
    public CommentResponse createComment(@PathVariable Long postId,
                                         @Valid @RequestBody CreateCommentRequest request) {
        return commentService.createComment(postId, request);
    }

    @PutMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable Long commentId,
                                         @Valid @RequestBody CreateCommentRequest request) {
        return commentService.updateComment(commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}