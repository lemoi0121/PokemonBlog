package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.CommentResponse;
import com.pokemon.blog.dto.request.CreateCommentRequest;
import java.util.List;

public interface CommentService {

    /**
     * Lấy tất cả comments của một post.
     *
     * @param postId ID của post
     * @return danh sách CommentResponse
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     */
    List<CommentResponse> getCommentsByPost(Long postId);

    /**
     * Tạo comment mới trên post.
     *
     * @param postId ID của post cần comment
     * @param request nội dung comment
     * @return CommentResponse của comment vừa tạo
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user chưa đăng nhập
     */
    CommentResponse createComment(Long postId, CreateCommentRequest request);

    /**
     * Cập nhật comment (chỉ chủ comment).
     *
     * @param commentId ID của comment cần update
     * @param request nội dung comment mới
     * @return CommentResponse cập nhật
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu comment không tồn tại
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user không có quyền
     */
    CommentResponse updateComment(Long commentId, CreateCommentRequest request);

    /**
     * Xóa comment (chỉ chủ comment hoặc admin).
     *
     * @param commentId ID của comment cần xóa
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu comment không tồn tại
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user không có quyền
     */
    void deleteComment(Long commentId);
}