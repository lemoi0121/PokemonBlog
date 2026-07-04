package com.pokemon.blog.service.impl;

import com.pokemon.blog.entity.User;
import com.pokemon.blog.dto.response.UserResponse;
import com.pokemon.blog.dto.request.CreateUserRequest;
import com.pokemon.blog.exception.DuplicateResourceException;
import com.pokemon.blog.exception.ResourceNotFoundException;
import com.pokemon.blog.repository.UserRepository;
import com.pokemon.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        logger.info("Lấy tất cả users");
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
        logger.debug("Tổng số users: {}", users.size());
        return users;
    }

    @Override
    public UserResponse getUser(Long id) {
        logger.info("Lấy user với id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User không tìm thấy với id: {}", id);
                    return new ResourceNotFoundException("Không tìm thấy User với id " + id);
                });
        return UserResponse.fromEntity(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        logger.info("Tạo user mới với username: {}", request.getUserName());

        if(userRepository.findByUserName(request.getUserName()).isPresent()) {
            logger.warn("Username {} đã tồn tại", request.getUserName());
            throw new DuplicateResourceException("Username đã tồn tại");
        }

        User user = new User();
        user.setName(request.getName());
        user.setUserName(request.getUserName());
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setRole(request.getRole());

        try {
            User saved = userRepository.save(user);
            logger.info("User {} (id: {}) được tạo thành công với role: {}",
                    saved.getUserName(), saved.getId(), saved.getRole());
            return UserResponse.fromEntity(saved);
        } catch (Exception e) {
            logger.error("Lỗi khi tạo user {}: {}", request.getUserName(), e.getMessage(), e);
            throw e;
        }
    }
}