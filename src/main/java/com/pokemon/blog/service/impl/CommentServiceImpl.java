package com.pokemon.blog.service.impl;

import com.pokemon.blog.entity.Comment;
import com.pokemon.blog.entity.Post;
import com.pokemon.blog.entity.User;
import com.pokemon.blog.dto.response.CommentResponse;
import com.pokemon.blog.dto.request.CreateCommentRequest;
import com.pokemon.blog.exception.ResourceNotFoundException;
import com.pokemon.blog.exception.UnauthorizedException;
import com.pokemon.blog.repository.CommentRepository;
import com.pokemon.blog.repository.PostRepository;
import com.pokemon.blog.service.CommentService;
import com.pokemon.blog.util.SecurityUtils;
import com.pokemon.blog.validator.CommentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<CommentResponse> getCommentsByPost(Long postId) {
        logger.info("Lấy comments của post: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", postId);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + postId);
                });

        List<CommentResponse> comments = commentRepository.findByPost(post)
                .stream()
                .map(CommentResponse::fromEntity)
                .toList();

        logger.debug("Post {} có {} comments", postId, comments.size());
        return comments;
    }

    @Override
    public CommentResponse createComment(Long postId, CreateCommentRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} tạo comment trên post: {}", currentUser.getUserName(), postId);

        // ✅ Business validation: content length
        if (!CommentValidator.isContentValid(request.getContent())) {
            logger.warn("Comment content không hợp lệ");
            throw new IllegalArgumentException(CommentValidator.getErrorMessage());
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.warn("Post không tìm thấy với id: {}", postId);
                    return new ResourceNotFoundException("Không tìm thấy Post với id " + postId);
                });

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setUser(currentUser);

        try {
            Comment saved = commentRepository.save(comment);
            logger.info("Comment {} được tạo thành công bởi user {} trên post {}",
                    saved.getId(), currentUser.getUserName(), postId);
            return CommentResponse.fromEntity(saved);
        } catch (Exception e) {
            logger.error("Lỗi khi tạo comment trên post {}: {}", postId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public CommentResponse updateComment(Long commentId, CreateCommentRequest request) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} cập nhật comment: {}", currentUser.getUserName(), commentId);

        // ✅ Business validation: content length
        if (!CommentValidator.isContentValid(request.getContent())) {
            logger.warn("Comment content không hợp lệ");
            throw new IllegalArgumentException(CommentValidator.getErrorMessage());
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    logger.warn("Comment không tìm thấy với id: {}", commentId);
                    return new ResourceNotFoundException("Không tìm thấy Comment với id " + commentId);
                });

        if (!SecurityUtils.isOwnerOrAdmin(comment.getUser().getId())) {
            logger.warn("User {} không có quyền cập nhật comment {}", currentUser.getUserName(), commentId);
            throw new UnauthorizedException("Bạn không có quyền sửa comment này");
        }

        comment.setContent(request.getContent());
        try {
            Comment saved = commentRepository.save(comment);
            logger.info("Comment {} được cập nhật thành công bởi user {}", commentId, currentUser.getUserName());
            return CommentResponse.fromEntity(saved);
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật comment {}: {}", commentId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        User currentUser = SecurityUtils.getCurrentUser();
        logger.info("User {} xóa comment: {}", currentUser.getUserName(), commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    logger.warn("Comment không tìm thấy với id: {}", commentId);
                    return new ResourceNotFoundException("Không tìm thấy Comment với id " + commentId);
                });

        if (!SecurityUtils.isOwnerOrAdmin(comment.getUser().getId())) {
            logger.warn("User {} không có quyền xóa comment {}", currentUser.getUserName(), commentId);
            throw new UnauthorizedException("Bạn không có quyền xóa comment này");
        }

        try {
            commentRepository.delete(comment);
            logger.info("Comment {} được xóa thành công bởi user {}", commentId, currentUser.getUserName());
        } catch (Exception e) {
            logger.error("Lỗi khi xóa comment {}: {}", commentId, e.getMessage(), e);
            throw e;
        }
    }
}