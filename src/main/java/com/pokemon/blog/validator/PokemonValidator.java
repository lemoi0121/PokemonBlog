package com.pokemon.blog.validator;

/**
 * Validator cho Pokemon.
 */
public class PokemonValidator {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_TOTAL_STATS = 600;
    private static final int MIN_STAT_VALUE = 1;
    private static final int MAX_STAT_VALUE = 255;

    /**
     * Kiểm tra Pokemon name hợp lệ.
     *
     * @param name name cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isNameValid(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        int length = name.trim().length();
        return length >= MIN_NAME_LENGTH && length <= MAX_NAME_LENGTH;
    }

    /**
     * Kiểm tra type không rỗng.
     *
     * @param type type cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isTypeValid(String type) {
        return type != null && !type.isBlank();
    }

    /**
     * Kiểm tra tất cả stats hợp lệ.
     * Mỗi stat phải trong range [1, 255] và tổng <= 600.
     *
     * @param hp HP stat
     * @param attack Attack stat
     * @param defense Defense stat
     * @param spAtk Special Attack stat
     * @param spDef Special Defense stat
     * @param speed Speed stat
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean areStatsValid(int hp, int attack, int defense,
                                        int spAtk, int spDef, int speed) {
        // Kiểm tra từng stat trong range
        if (!isStatInRange(hp) || !isStatInRange(attack) || !isStatInRange(defense) ||
                !isStatInRange(spAtk) || !isStatInRange(spDef) || !isStatInRange(speed)) {
            return false;
        }

        // Kiểm tra tổng stats
        int totalStats = hp + attack + defense + spAtk + spDef + speed;
        return totalStats <= MAX_TOTAL_STATS;
    }

    /**
     * Kiểm tra một stat có trong range hợp lệ.
     *
     * @param stat stat cần kiểm tra
     * @return true nếu trong range [1, 255]
     */
    private static boolean isStatInRange(int stat) {
        return stat >= MIN_STAT_VALUE && stat <= MAX_STAT_VALUE;
    }

    /**
     * Lấy message lỗi name.
     *
     * @return error message
     */
    public static String getNameErrorMessage() {
        return "Pokemon name phải từ " + MIN_NAME_LENGTH + " đến " + MAX_NAME_LENGTH + " ký tự";
    }

    /**
     * Lấy message lỗi stats.
     *
     * @return error message
     */
    public static String getStatsErrorMessage() {
        return "Mỗi stat phải trong range 1-255 và tổng stats <= " + MAX_TOTAL_STATS;
    }
}