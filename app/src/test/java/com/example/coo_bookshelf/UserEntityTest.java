package com.example.coo_bookshelf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.example.coo_bookshelf.database.entities.User;

import org.junit.Test;

/**
 * Author: Armando Sarza
 * Date: 2025-12-14
 * Description: Tests that a newly created User is not an admin by default.
 *  -> Creates a User using the email + password constructor.
 *  -> Verifies the email and password fields are stored correctly.
 *  -> Verifies the isAdmin flag is set to false for the new User.
 */
public class UserEntityTest {

    @Test
    public void newUser_isNotAdminByDefault() {
        // Make a new user using the same constructor used in sign up
        String email = "test@csumb.edu";
        String password = "testpassword";

        User user = new User(email, password);

        // Verify that email and password are stored correctly using assertions
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());

        // Verify that the admin flag starts as false
        assertFalse("New users should not be admins by default", user.isAdmin());

    }
}
