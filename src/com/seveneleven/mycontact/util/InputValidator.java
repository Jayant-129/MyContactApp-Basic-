// Utility class providing static validation methods for user inputs like email, phone, and name
package com.seveneleven.mycontact.util;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^[+]?[0-9]{7,15}$");

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static void requireNonEmpty(String value, String fieldName) {
        if (!isNotEmpty(value))
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
    }
}
