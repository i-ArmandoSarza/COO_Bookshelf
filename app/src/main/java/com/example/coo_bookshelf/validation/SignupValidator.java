package com.example.coo_bookshelf.validation;

public class SignupValidator {

    // Check basic email format
    public static boolean isEmailValid(String email) {
        if(email == null || email.trim().isEmpty()) return false;

        email = email.trim();

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        // '@' must exist and not be the first char
        if(atIndex <= 0) return false;

        // '.' must exist after '@'
        if(dotIndex <= atIndex) return false;

        // '.' must not be the last char
        if(dotIndex == email.length()-1) return false;

        return true;
    }

    // Password must be at least 4 characters
    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 4;
    }

    // Signup Validation
    public static boolean canSignup(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }
}
