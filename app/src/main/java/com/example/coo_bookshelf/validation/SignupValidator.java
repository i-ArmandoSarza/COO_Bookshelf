package com.example.coo_bookshelf.validation;

public class SignupValidator {

    // Check basic email format
    public static boolean isEmailValid(String email) {
        if(email == null || email.trim().isEmpty()) return false;
        if(!email.contains("@")) return false;
        if(!email.contains(".")) return false;

        // "." must appear after "@"
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        return dotIndex > atIndex;
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
