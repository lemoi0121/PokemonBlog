package com.pokemon.blog.service.impl;

import com.pokemon.blog.entity.Post;
import com.pokemon.blog.entity.User;
import com.pokemon.blog.entity.Role;
import com.pokemon.blog.dto.response.PostResponse;
import com.pokemon.blog.dto.request.CreatePostRequest;
import com.pokemon.blog.exception.ResourceNotFoundException;
import com.pokemon.blog.exception.UnauthorizedException;
import com.pokemon.blog.repository.PostRepository;
import com.pokemon.blog.service.PostService;
import com.pokemon.blog.util.SecurityUtils;
import com.pokemon.blog.validator.PostValidator;
import com.pokemon.blog.validator.SearchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostResponse> getAllPosts() {
        logger.info("Lấy tất cả posts");
        List<PostResponse> posts = postRepository.findAll()
                .stream()
                .map(PostResponse::fromEntity)
                .toList();
        logger.debug("Tổng số posts: {}", posts.size());
        return posts;
    }

    @Override
    public PostResponse getPost(Long id) {
        logger.info("Lấy post với id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + id);
                });
        return PostResponse.fromEntity(post);
    }

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} tạo post mới với tiêu đề: {}", currentUser.getUserName(), request.getTitle());

        // ✅ Business validation: title format
        if (!PostValidator.isTitleValid(request.getTitle())) {
            logger.warn("Title không hợp lệ: {}", request.getTitle());
            throw new IllegalArgumentException(PostValidator.getTitleErrorMessage());
        }

        // ✅ Business validation: content cleanliness
        if (!PostValidator.isContentClean(request.getContent())) {
            logger.warn("Content chứa từ cấm");
            throw new IllegalArgumentException(PostValidator.getContentErrorMessage());
        }

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(currentUser);

        try {
            Post saved = postRepository.save(post);
            logger.info("Post {} được tạo thành công bởi user {}", saved.getId(), currentUser.getUserName());
            return PostResponse.fromEntity(saved);
        } catch (Exception e) {
            logger.error("Lỗi khi tạo post cho user {}: {}", currentUser.getUserName(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PostResponse updatePost(Long id, CreatePostRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} cố cập nhật post với id: {}", currentUser.getUserName(), id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + id);
                });

        if (!SecurityUtils.isOwnerOrAdmin(post.getAuthor().getId())) {
            logger.warn("User {} không có quyền cập nhật post {}", currentUser.getUserName(), id);
            throw new UnauthorizedException("Bạn không có quyền sửa post này");
        }

        // ✅ Business validation: title format
        if (!PostValidator.isTitleValid(request.getTitle())) {
            logger.warn("Title không hợp lệ: {}", request.getTitle());
            throw new IllegalArgumentException(PostValidator.getTitleErrorMessage());
        }

        // ✅ Business validation: content cleanliness
        if (!PostValidator.isContentClean(request.getContent())) {
            logger.warn("Content chứa từ cấm");
            throw new IllegalArgumentException(PostValidator.getContentErrorMessage());
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        try {
            Post saved = postRepository.save(post);
            logger.info("Post {} được cập nhật thành công bởi user {}", id, currentUser.getUserName());
            return PostResponse.fromEntity(saved);
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật post {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deletePost(Long id) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} xóa post với id: {}", currentUser.getUserName(), id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + id);
                });

        if (!SecurityUtils.isOwnerOrAdmin(post.getAuthor().getId())) {
            logger.warn("User {} không có quyền xóa post {}", currentUser.getUserName(), id);
            throw new UnauthorizedException("Bạn không có quyền xóa post này");
        }

        try {
            postRepository.deleteById(id);
            logger.info("Post {} được xóa thành công bởi user {}", id, currentUser.getUserName());
        } catch (Exception e) {
            logger.error("Lỗi khi xóa post {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<PostResponse> searchPosts(String keyword) {
        logger.info("Tìm kiếm posts với keyword: {}", keyword);

        // ✅ Business validation: keyword format
        if (!SearchValidator.isKeywordValid(keyword)) {
            logger.warn("Keyword không hợp lệ: {}", keyword);
            throw new IllegalArgumentException(SearchValidator.getErrorMessage());
        }

        List<PostResponse> results = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(PostResponse::fromEntity)
                .toList();

        logger.info("Tìm thấy {} posts với keyword: {}", results.size(), keyword);
        return results;
    }
}