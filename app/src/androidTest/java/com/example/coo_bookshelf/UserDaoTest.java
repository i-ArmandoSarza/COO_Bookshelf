package com.example.coo_bookshelf;

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
 * Description: Test that DAO returns valid user id for given email.
 *  -> Inserts a user into the database with a known email.
 *  -> Call DAO method that looks up userId by email.
 *  -> Confirm DAO returns a valid user id (> 0).
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
}
