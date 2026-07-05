package com.pokemon.blog.validator;

import java.util.regex.Pattern;

/**
 * Validator cho username.
 */
public class UsernameValidator {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,50}$");

    /**
     * Kiểm tra username hợp lệ.
     * Username chỉ được chứa: chữ cái, số, dấu gạch dưới, dấu gạch ngang.
     * Độ dài: 3-50 ký tự.
     *
     * @param username username cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isValid(String username) {
        if (username == null || username.isBlank()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Lấy message lỗi nếu username không hợp lệ.
     *
     * @return error message
     */
    public static String getErrorMessage() {
        return "Username chỉ được chứa chữ cái, số, dấu gạch dưới, dấu gạch ngang (3-50 ký tự)";
    }
}