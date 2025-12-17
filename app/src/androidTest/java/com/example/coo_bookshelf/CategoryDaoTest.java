package com.example.coo_bookshelf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.DAO.CategoryDAO;
import com.example.coo_bookshelf.database.entities.Category;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Tests for the Category table.
 * Each test checks if insert, update, or delete behaves as expected.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {

    /**
     * Makes LiveData run right away in tests.
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BookshelfDatabase db;
    private CategoryDAO categoryDAO;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // In-memory database lives only while tests run
        db = Room.inMemoryDatabaseBuilder(context, BookshelfDatabase.class)
                .allowMainThreadQueries()
                .build();
        categoryDAO = db.categoryDAO();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Before
    public void setup() {
        db.clearAllTables();
    }

    /**
     * Test 1:
     * Inserts one Category and checks if the table holds it.
     */
    @Test
    public void insertCategory_savesCategory() throws InterruptedException {
        // Step 1: Make a category object
        Category category = new Category("Sci-Fi");

        // Step 2: Insert it into the database
        categoryDAO.insert(category);

        // Step 3: Read all categories from the table
        LiveData<List<Category>> categoriesObserver = categoryDAO.getAllCategories();
        List<Category> categories = LiveDataTestUtil.getOrAwaitValue(categoriesObserver);

        // Step 4: Check the list has one item with the right name
        assertEquals(1, categories.size());
        assertEquals("Sci-Fi", categories.get(0).getCategoryName());
    }

    /**
     * Test 2:
     * Updates a category name and checks if the new name shows up.
     */
    @Test
    public void updateCategory_updatesName() throws InterruptedException {
        // Step 1: Insert a starting category
        Category category = new Category("Sci-Fi");
        categoryDAO.insert(category);

        // Step 2: Read it back
        LiveData<List<Category>> categoriesObserver = categoryDAO.getAllCategories();
        List<Category> categories = LiveDataTestUtil.getOrAwaitValue(categoriesObserver);
        Category saved = categories.get(0);

        // Step 3: Change the name and update the row
        saved.setCategoryName("Science Fiction");
        categoryDAO.update(saved);

        // Step 4: Read the table again and check the new name
        categoriesObserver = categoryDAO.getAllCategories();
        List<Category> updatedCategories = LiveDataTestUtil.getOrAwaitValue(categoriesObserver);
        Category updated = updatedCategories.get(0);

        assertEquals("Science Fiction", updated.getCategoryName());
    }

    /**
     * Test 3:
     * Deletes a category and checks if the table becomes empty.
     */
    @Test
    public void deleteCategory_removesRow() throws InterruptedException {
        // Step 1: Insert a category
        Category category = new Category("Sci-Fi");
        categoryDAO.insert(category);

        // Step 2: Make sure it exists
        LiveData<List<Category>> categoriesObserver = categoryDAO.getAllCategories();
        List<Category> categories = LiveDataTestUtil.getOrAwaitValue(categoriesObserver);
        assertEquals(1, categories.size());

        // Step 3: Delete the saved category
        Category saved = categories.get(0);
        categoryDAO.delete(saved);

        // Step 4: Read again and check if list is empty
        categoriesObserver = categoryDAO.getAllCategories();
        List<Category> afterDelete = LiveDataTestUtil.getOrAwaitValue(categoriesObserver);
        assertTrue(afterDelete.isEmpty());
    }
}
