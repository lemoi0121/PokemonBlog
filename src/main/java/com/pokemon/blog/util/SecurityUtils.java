package com.pokemon.blog.util;

import com.pokemon.blog.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * Lấy user hiện tại từ SecurityContextHolder.
     *
     * @return User object của user đang đăng nhập
     * @throws ClassCastException nếu principal không phải User
     */
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
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
     *
     * @param userId ID của user cần kiểm tra
     * @return true nếu user hiện tại là chủ resource hoặc là ADMIN
     */
    public static boolean isOwnerOrAdmin(Long userId) {
        User currentUser = getCurrentUser();
        return currentUser.getId().equals(userId) || isAdmin();
    }
}