package com.pokemon.blog.service;

public interface PostLikeService {

    /**
     * Like một post.
     *
     * @param postId ID của post cần like
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     * @throws com.pokemon.blog.exception.DuplicateResourceException nếu user đã like post này
     */
    void likePost(Long postId);

    /**
     * Bỏ like post.
     *
     * @param postId ID của post cần bỏ like
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại hoặc user chưa like
     */
    void unlikePost(Long postId);

    /**
     * Lấy số like của post.
     *
     * @param postId ID của post
     * @return số lượng like
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     */
    Long getLikeCount(Long postId);
}