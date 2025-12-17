package com.example.coo_bookshelf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.coo_bookshelf.validation.LoginValidator;

import org.junit.Test;

public class LoginValidatorTest {

    // ---- Email Test ----
    @SuppressWarnings("ConstantConditions")
    @Test
    public void email_null_shouldBeInvalid() {
        assertFalse(LoginValidator.isEmailValid(null));
    }

    @Test
    public void email_missingAtSymbol_shouldBeInvalid(){
        assertFalse(LoginValidator.isEmailValid("testcsumb.edu"));
    }

    // ---- Password Tests ----
    @Test
    public void password_null_shouldBeInvalid() {
        assertFalse(LoginValidator.isPasswordValid(null));
    }

    @Test
    public void password_emptyOrSpaces_shouldBeInvalid() {
        assertFalse(LoginValidator.isPasswordValid(""));
        assertFalse(LoginValidator.isPasswordValid("   "));
    }

    @Test
    public void password_notEmpty_shouldBeValid() {
        assertTrue(LoginValidator.isPasswordValid("mypassword"));
    }

    // ---- Overall Login Test ----
    @Test
    public void canLogin_validEmailAndPassword_shouldReturnTrue() {
        assertTrue(LoginValidator.canLogin("user@test.com", "password123"));
    }

    @Test
    public void canLogin_invalidEmail_shouldReturnFalse() {
        assertFalse(LoginValidator.canLogin("bademail.com", "password123"));
    }

    @Test
    public void canLogin_invalidPassword_shouldReturnFalse() {
        assertFalse(LoginValidator.canLogin("user@test.com", ""));
    }


}
