package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.CommentResponse;
import com.pokemon.blog.dto.request.CreateCommentRequest;
import java.util.List;

public interface CommentService {
    List<CommentResponse> getCommentsByPost(Long postId);

    CommentResponse createComment(Long postId, CreateCommentRequest request);

    CommentResponse updateComment(Long commentId, CreateCommentRequest request);

    void deleteComment(Long commentId);
}