package com.pokemon.blog.validator;

/**
 * Validator cho Comment.
 */
public class CommentValidator {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 1000;

    /**
     * Kiểm tra comment content hợp lệ.
     *
     * @param content content cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isContentValid(String content) {
        if (content == null || content.isBlank()) {
            return false;
        }
        int length = content.trim().length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }

    /**
     * Lấy message lỗi.
     *
     * @return error message
     */
    public static String getErrorMessage() {
        return "Comment phải từ " + MIN_LENGTH + " đến " + MAX_LENGTH + " ký tự";
    }
}