package com.pokemon.blog.validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Validator cho Post content.
 */
public class PostValidator {

    private static final Set<String> FORBIDDEN_WORDS = new HashSet<>(Set.of(
            "spam", "violence", "hate", "abuse", "profanity"
    ));

    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 200;

    /**
     * Kiểm tra title hợp lệ.
     *
     * @param title title cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isTitleValid(String title) {
        if (title == null || title.isBlank()) {
            return false;
        }
        int length = title.trim().length();
        return length >= MIN_TITLE_LENGTH && length <= MAX_TITLE_LENGTH;
    }

    /**
     * Kiểm tra content không chứa từ cấm.
     *
     * @param content content cần kiểm tra
     * @return true nếu hợp lệ, false nếu chứa từ cấm
     */
    public static boolean isContentClean(String content) {
        if (content == null || content.isBlank()) {
            return true;
        }

        String lowerContent = content.toLowerCase();
        return FORBIDDEN_WORDS.stream()
                .noneMatch(lowerContent::contains);
    }

    /**
     * Lấy message lỗi title.
     *
     * @return error message
     */
    public static String getTitleErrorMessage() {
        return "Title phải từ " + MIN_TITLE_LENGTH + " đến " + MAX_TITLE_LENGTH + " ký tự";
    }

    /**
     * Lấy message lỗi content.
     *
     * @return error message
     */
    public static String getContentErrorMessage() {
        return "Content chứa từ không được phép";
    }
}