package com.pokemon.blog.service;

import com.pokemon.blog.dto.response.UserResponse;
import com.pokemon.blog.dto.response.PaginationResponse;
import com.pokemon.blog.dto.request.CreateUserRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {

    /**
     * Lấy tất cả users (phân trang).
     *
     * @param pageable Spring Data Pageable object
     * @return PaginationResponse chứa users + metadata
     */
    PaginationResponse<UserResponse> getAllUsers(Pageable pageable);

    /**
     * Lấy chi tiết một user.
     *
     * @param id ID của user
     * @return UserResponse nếu tìm thấy
     * @throws com.pokemon.blog.exception.ResourceNotFoundException nếu user không tồn tại
     */
    UserResponse getUser(Long id);

    /**
     * Tạo user mới (đăng ký).
     *
     * @param request thông tin user (name, username, password, role)
     * @return UserResponse của user vừa tạo
     * @throws com.pokemon.blog.exception.DuplicateResourceException nếu username đã tồn tại
     */
    UserResponse createUser(CreateUserRequest request);
}