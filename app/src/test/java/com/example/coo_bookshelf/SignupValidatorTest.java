package com.example.coo_bookshelf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.coo_bookshelf.validation.SignupValidator;

import org.junit.Test;

public class SignupValidatorTest {

    // ---- Email Validation Tests ----
    @Test
    public void email_empty_shouldBeInvalid() {
        assertFalse(SignupValidator.isEmailValid(""));
        assertFalse(SignupValidator.isEmailValid("   "));
        assertFalse(SignupValidator.isEmailValid(null));
    }

    @Test
    public void email_missingAtSymbol_shouldBeInvalid() {
        assertFalse(SignupValidator.isEmailValid("userexample.com"));
    }

    @Test
    public void email_missingDot_shouldBeInvalid() {
        assertFalse(SignupValidator.isEmailValid("user@examplecom"));
    }

    @Test
    public void email_dotBeforeAt_shouldBeInvalid() {
        assertFalse(SignupValidator.isEmailValid("user.example@com"));
    }

    @Test
    public void email_validFormat_shouldBeValid() {
        assertTrue(SignupValidator.isEmailValid("user@example.com"));
        assertTrue(SignupValidator.isEmailValid("name.something@domain.com"));
    }

    @Test
    public void email_dotAtEnd_shouldBeInvalid() {
        assertFalse(SignupValidator.isEmailValid("test@csumb."));
    }

    // ---- Password Validation Test ----
    @SuppressWarnings("ConstantConditions")
    @Test
    public void password_null_shouldBeInvalid() {
        assertFalse(SignupValidator.isPasswordValid(null));
    }

    @Test
    public void password_tooShort_shouldBeInvalid() {
        assertFalse(SignupValidator.isPasswordValid("123"));
    }

    @Test
    public void password_validLength_shouldBeValid() {
        assertTrue(SignupValidator.isPasswordValid("1234"));
        assertTrue(SignupValidator.isPasswordValid("password1234"));
    }

    // ---- Overall Signup Test ----
    @Test
    public void signup_invalidEmail_shouldFail() {
        assertFalse(SignupValidator.canSignup("invalidemail", "password123"));
    }

    @Test
    public void signup_invalidPassword_shouldFail() {
        assertFalse(SignupValidator.canSignup("user@example.com", "12"));
    }

    @Test
    public void signup_validInput_shouldPass() {
        assertTrue(SignupValidator.canSignup("user@example.com", "password123"));
    }
}
