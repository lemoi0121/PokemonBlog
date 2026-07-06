package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.PostResponse;
import com.pokemon.blog.dto.response.PaginationResponse;
import com.pokemon.blog.dto.request.CreatePostRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostService {

    /**
     * Lấy tất cả posts (phân trang).
     *
     * @param pageable Spring Data Pageable object chứa page, size, sort
     * @return PaginationResponse chứa posts + metadata phân trang
     */
    PaginationResponse<PostResponse> getAllPosts(Pageable pageable);

    /**
     * Lấy chi tiết một post.
     *
     * @param id ID của post
     * @return PostResponse nếu tìm thấy
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     */
    PostResponse getPost(Long id);

    /**
     * Tạo post mới cho user hiện tại.
     *
     * @param request thông tin post (title, content)
     * @return PostResponse của post vừa tạo
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user chưa đăng nhập
     */
    PostResponse createPost(CreatePostRequest request);

    /**
     * Cập nhật post (chỉ chủ post hoặc admin).
     *
     * @param id ID của post cần update
     * @param request thông tin post mới
     * @return PostResponse cập nhật
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user không có quyền
     */
    PostResponse updatePost(Long id, CreatePostRequest request);

    /**
     * Xóa post (chỉ chủ post hoặc admin).
     *
     * @param id ID của post cần xóa
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu post không tồn tại
     * @throws com.pokemon.blog.exception.UnauthorizedException nếu user không có quyền
     */
    void deletePost(Long id);

    /**
     * Tìm kiếm posts theo keyword (phân trang).
     *
     * @param keyword từ khóa tìm kiếm (tìm trong title và content)
     * @param pageable Spring Data Pageable object
     * @return PaginationResponse chứa posts tìm thấy
     * @throws IllegalArgumentException nếu keyword không hợp lệ
     */
    PaginationResponse<PostResponse> searchPosts(String keyword, Pageable pageable);
}