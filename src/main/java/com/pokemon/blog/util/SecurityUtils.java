package com.pokemon.blog.util;

import com.pokemon.blog.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class cho việc lấy thông tin security từ SecurityContextHolder.
 */
public class SecurityUtils {

    /**
     * Lấy user hiện tại từ SecurityContextHolder.
     *
     * @return User object của user đang đăng nhập
     * @throws ClassCastException nếu principal không phải User
     */
    public static User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                !SecurityContextHolder.getContext().getAuthentication().isAuthenticated() ||
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new com.pokemon.blog.exception.UnauthorizedException("Bạn cần đăng nhập để thực hiện hành động này");
        }
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User)) {
            throw new com.pokemon.blog.exception.UnauthorizedException("Thông tin xác thực không hợp lệ");
        }
        
        return (User) principal;
    }

    /**
     * Lấy ID của user hiện tại.
     *
     * @return ID của user đang đăng nhập
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Lấy username của user hiện tại.
     *
     * @return Username của user đang đăng nhập
     */
    public static String getCurrentUsername() {
        return getCurrentUser().getUserName();
    }

    /**
     * Kiểm tra xem user hiện tại có phải Admin không.
     *
     * @return true nếu user là ADMIN, false nếu không
     */
    public static boolean isAdmin() {
        return getCurrentUser().getRole().name().equals("ADMIN");
    }

    /**
     * Kiểm tra xem user hiện tại có phải chủ của resource không.
     * Người dùng chủ resource hoặc là admin sẽ trả về true.
     *
     * @param userId ID của user cần kiểm tra
     * @return true nếu user hiện tại là chủ resource hoặc là ADMIN, false nếu không
     */
    public static boolean isOwnerOrAdmin(Long userId) {
        User currentUser = getCurrentUser();
        return currentUser.getId().equals(userId) || isAdmin();
    }
}