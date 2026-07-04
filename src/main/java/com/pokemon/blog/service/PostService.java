package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.PostResponse;
import com.pokemon.blog.dto.request.CreatePostRequest;
import java.util.List;

public interface PostService {
    List<PostResponse> getAllPosts();

    PostResponse getPost(Long id);

    PostResponse createPost(CreatePostRequest request);

    PostResponse updatePost(Long id, CreatePostRequest request);

    void deletePost(Long id);

    List<PostResponse> searchPosts(String keyword);
}