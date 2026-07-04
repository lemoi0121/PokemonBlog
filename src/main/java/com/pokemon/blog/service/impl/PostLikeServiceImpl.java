package com.pokemon.blog.service.impl;

import com.pokemon.blog.entity.Post;
import com.pokemon.blog.entity.PostLike;
import com.pokemon.blog.entity.User;
import com.pokemon.blog.exception.DuplicateResourceException;
import com.pokemon.blog.exception.ResourceNotFoundException;
import com.pokemon.blog.repository.PostLikeRepository;
import com.pokemon.blog.repository.PostRepository;
import com.pokemon.blog.service.PostLikeService;
import com.pokemon.blog.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    private static final Logger logger = LoggerFactory.getLogger(PostLikeServiceImpl.class);

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void likePost(Long postId) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} like post: {}", currentUser.getUserName(), postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", postId);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + postId);
                });

        if (postLikeRepository.findByPostAndUser(post, currentUser).isPresent()) {
            logger.warn("User {} đã like post {} rồi", currentUser.getUserName(), postId);
            throw new DuplicateResourceException("Bạn đã like post này rồi");
        }

        try {
            PostLike postLike = new PostLike(post, currentUser);
            postLikeRepository.save(postLike);
            logger.info("User {} like post {} thành công", currentUser.getUserName(), postId);
        } catch (Exception e) {
            logger.error("Lỗi khi like post {}: {}", postId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void unlikePost(Long postId) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} bỏ like post: {}", currentUser.getUserName(), postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", postId);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + postId);
                });

        PostLike postLike = postLikeRepository.findByPostAndUser(post, currentUser)
                .orElseThrow(() -> {
                    logger.warn("User {} chưa like post {}", currentUser.getUserName(), postId);
                    return new ResourceNotFoundException("Bạn chưa like post này");
                });

        try {
            postLikeRepository.delete(postLike);
            logger.info("User {} bỏ like post {} thành công", currentUser.getUserName(), postId);
        } catch (Exception e) {
            logger.error("Lỗi khi bỏ like post {}: {}", postId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Long getLikeCount(Long postId) {
        logger.debug("Lấy số like của post: {}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", postId);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + postId);
                });

        Long likeCount = postLikeRepository.countByPost(post);
        logger.debug("Post {} có {} likes", postId, likeCount);
        return likeCount;
    }
}