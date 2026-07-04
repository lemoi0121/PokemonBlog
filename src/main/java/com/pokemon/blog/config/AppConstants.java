package com.pokemon.blog.config;

public class AppConstants {

    // ===== JWT Configuration =====
    public static final long JWT_TOKEN_EXPIRY = 86400000L; // 24 hours in milliseconds
    public static final String JWT_BEARER_PREFIX = "Bearer ";
    public static final String JWT_ROLE_PREFIX = "ROLE_";

    // ===== Validation =====
    public static final int POST_CONTENT_MIN_LENGTH = 10;
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int POKEMON_NAME_MIN_LENGTH = 2;

    // ===== Error Messages =====
    public static final String ERROR_POST_NOT_FOUND = "Không tìm thấy Post với id ";
    public static final String ERROR_POKEMON_NOT_FOUND = "Không tìm thấy Pokemon với id ";
    public static final String ERROR_USER_NOT_FOUND = "Không tìm thấy User với id ";
    public static final String ERROR_COMMENT_NOT_FOUND = "Không tìm thấy Comment với id ";
    public static final String ERROR_POST_LIKED = "Bạn đã like post này rồi";
    public static final String ERROR_POST_NOT_LIKED = "Bạn chưa like post này";
    public static final String ERROR_UNAUTHORIZED = "Bạn không có quyền thực hiện hành động này";
    public static final String ERROR_ADMIN_REQUIRED = "Chỉ Admin mới có quyền thực hiện hành động này";
    public static final String ERROR_USERNAME_EXISTS = "Username đã tồn tại";
    public static final String ERROR_POKEMON_EXISTS = "Pokemon đã tồn tại";
    public static final String ERROR_INVALID_PASSWORD = "Mật khẩu không đúng";
    public static final String ERROR_EMPTY_KEYWORD = "Keyword không được để trống";

    // ===== Success Messages =====
    public static final String SUCCESS_POST_CREATED = "Post được tạo thành công";
    public static final String SUCCESS_POKEMON_CREATED = "Pokemon được tạo thành công";

    // ===== Validation Messages =====
    public static final String VALIDATION_TITLE_REQUIRED = "Title không được trống";
    public static final String VALIDATION_CONTENT_REQUIRED = "Content không được trống";
    public static final String VALIDATION_CONTENT_MIN_LENGTH = "Content tối thiểu " + POST_CONTENT_MIN_LENGTH + " ký tự";
    public static final String VALIDATION_USERNAME_REQUIRED = "Username không được trống";
    public static final String VALIDATION_PASSWORD_REQUIRED = "Password không được trống";
    public static final String VALIDATION_NAME_REQUIRED = "Tên không được trống";
    public static final String VALIDATION_ROLE_REQUIRED = "Role không được trống";
    public static final String VALIDATION_POKEMON_NAME_REQUIRED = "Tên Pokemon không được trống";
    public static final String VALIDATION_POKEMON_TYPE_REQUIRED = "Loại Pokemon không được trống";
    public static final String VALIDATION_COMMENT_REQUIRED = "Nội dung comment không được trống";

    // ===== CORS Configuration =====
    public static final String CORS_ALLOWED_ORIGIN = "http://localhost:3000";
    public static final int CORS_MAX_AGE = 3600; // 1 hour

    // ===== Database =====
    public static final int DEFAULT_PAGE_SIZE = 10;
}