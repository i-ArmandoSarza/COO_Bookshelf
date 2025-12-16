package com.example.coo_bookshelf.validation;

public class LoginValidator {

    // Email can't be null and must contain "@"
    public static boolean isEmailValid(String email) {
        return email != null && email.contains("@");
    }

    // Password can't be null or empty
    public static boolean isPasswordValid(String password) {
        return password != null && !password.trim().isEmpty();
    }

    // Both email & password must be valid
    public static boolean canLogin(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }
}
