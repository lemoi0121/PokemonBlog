package com.pokemon.blog.service;

public interface PostLikeService {
    void likePost(Long postId);

    void unlikePost(Long postId);

    Long getLikeCount(Long postId);
}