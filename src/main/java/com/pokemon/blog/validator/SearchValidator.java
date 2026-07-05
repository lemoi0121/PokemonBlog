package com.pokemon.blog.validator;

/**
 * Validator cho search keyword.
 */
public class SearchValidator {

    private static final int MIN_KEYWORD_LENGTH = 2;
    private static final int MAX_KEYWORD_LENGTH = 100;

    /**
     * Kiểm tra keyword hợp lệ.
     *
     * @param keyword keyword cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isKeywordValid(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }

        int length = keyword.trim().length();
        return length >= MIN_KEYWORD_LENGTH && length <= MAX_KEYWORD_LENGTH;
    }

    /**
     * Lấy message lỗi.
     *
     * @return error message
     */
    public static String getErrorMessage() {
        return "Keyword phải từ " + MIN_KEYWORD_LENGTH + " đến " + MAX_KEYWORD_LENGTH + " ký tự";
    }
}