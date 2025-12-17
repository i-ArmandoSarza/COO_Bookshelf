package com.example.coo_bookshelf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.DAO.UserDAO;
import com.example.coo_bookshelf.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Author: Armando Sarza
 * Date: 2025-12-14
 * Description: Three unit tests for UserDAO covering basic CRUD operations:
 *  -> Insert: a user into the database with a known email.
 *  -> Update: user name fields are updated correctly
 *  -> Delete: user record is removed from the database.
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private BookshelfDatabase db;
    private UserDAO userDAO;

    @Before
    public void createDb() {
        // Using in memory database for testing so data is not saved to disk
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, BookshelfDatabase.class)
                .allowMainThreadQueries()
                .build();

        userDAO = db.userDAO();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void getUserIdByUserEmailSync_returnsCorrectId() {
        // Insert a user with a known email
        String email = "DAO@csumb.edu";
        String password = "secretPassword";

        User user = new User(email, password);

        // Insert user into the test database
        userDAO.insert(user);

        // Get user id by email using DAO method
        int idFromDao = userDAO.getUserIdByUserEmailSync(email);

        // Assert id returned by DAO matches the user's id
        assertTrue("DAO should return a valid user id > 0",
                idFromDao > 0);
    }

    @Test
    public void updateUserName_updatesFirstAndLastName() {
        String email = "update@csumb.edu";
        String password = "secretPassword";

        // Insert user with initial name
        User user = new User(email, password);
        user.setFirstName("OldFirst");
        user.setLastName("OldLast");
        userDAO.insert(user);

        int userId = userDAO.getUserIdByUserEmailSync(email);
        assertTrue("User id should be > 0", userId > 0);

        // Call DAO update method
        userDAO.updateUserName(userId, "NewFirst", "NewLast");

        // Fetch user again and verify update
        User updatedUser = userDAO.getUserByEmailAndPasswordSync(email, password);
        assertNotNull("Updated user should not be null", updatedUser);
        assertEquals("NewFirst", updatedUser.getFirstName());
        assertEquals("NewLast", updatedUser.getLastName());
    }

    @Test
    public void deleteUser_removesRowFromTable() {
        String email = "delete@csumb.edu";
        String password = "secretPassword";

        // Insert user
        User user = new User(email, password);
        userDAO.insert(user);

        // Make sure it exists
        User inserted = userDAO.getUserByEmailAndPasswordSync(email, password);
        assertNotNull("User should exist after insert", inserted);

        // Delete user
        userDAO.delete(inserted);   // make sure you have @Delete void delete(User user); in UserDAO

        // And now it should be gone
        User deleted = userDAO.getUserByEmailAndPasswordSync(email, password);
        assertNull("User should be null after delete", deleted);
    }

}
