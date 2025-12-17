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
import com.example.coo_bookshelf.database.DAO.BookCategoryDAO;
import com.example.coo_bookshelf.database.DAO.BookDAO;
import com.example.coo_bookshelf.database.DAO.CategoryDAO;
import com.example.coo_bookshelf.database.DAO.UserDAO;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.BookCategory;
import com.example.coo_bookshelf.database.entities.Category;
import com.example.coo_bookshelf.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Tests for the BookCategory link table.
 * Each row connects one book id with one category id.
 */
@RunWith(AndroidJUnit4.class)
public class BookCategoryDaoTest {

    /**
     * Makes LiveData run right away in tests.
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BookshelfDatabase db;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private CategoryDAO categoryDAO;
    private BookCategoryDAO bookCategoryDAO;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, BookshelfDatabase.class)
                .allowMainThreadQueries()
                .build();

        userDAO = db.userDAO();
        bookDAO = db.bookDAO();
        categoryDAO = db.categoryDAO();
        bookCategoryDAO = db.bookCategoryDAO();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Before
    public void setup() {
        db.clearAllTables();
    }

    /** Inserts a Monty user and returns the new user id. */
    private int insertMontyUserAndGetId() {
        User userMonty = new User("monty_category@csumb.edu", "admin1");
        userMonty.setAdmin(true);
        userMonty.setFirstName("Monty");
        userMonty.setLastName("Rey");
        userDAO.insert(userMonty);

        int userId = userDAO.getUserIdByUserEmailSync("monty_category@csumb.edu");
        assertTrue("Monty user id should be > 0", userId > 0);
        return userId;
    }

    /** Inserts one book for the user and returns the new book id. */
    private int insertBookForUser(int userId) throws InterruptedException {
        Book montyBook = new Book(
                userId,
                "Red Rising",
                "Pierce Brown",
                "OL7621609A",
                "OL17076473W"
        );
        montyBook.setFirstPublishedYear("October 14, 2025");
        bookDAO.insert(montyBook);

        LiveData<List<Book>> booksObserver = bookDAO.getBooksByUserId(userId);
        List<Book> books = LiveDataTestUtil.getOrAwaitValue(booksObserver);
        assertEquals(1, books.size());
        return books.get(0).getBookId();
    }

    /** Inserts one category and returns its id. */
    private int insertCategoryAndGetId(String name) throws InterruptedException {
        Category category = new Category(name);
        categoryDAO.insert(category);

        LiveData<List<Category>> categoriesObserver = categoryDAO.getAllCategories();
        List<Category> categories = LiveDataTestUtil.getOrAwaitValue(categoriesObserver);

        // Find the category in the list that matches the name
        Category saved = categories.stream()
                .filter(c -> name.equals(c.getCategoryName()))
                .findFirst()
                .orElseThrow();
        return saved.getCategoryId();
    }

    /**
     * Test 1:
     * Inserts a link between a book and a category and checks if it exists.
     */
    @Test
    public void insertBookCategory_createsLinkBetweenBookAndCategory() throws InterruptedException {
        // Step 1: Prepare user, book, and category
        int userId = insertMontyUserAndGetId();
        int bookId = insertBookForUser(userId);
        int categoryId = insertCategoryAndGetId("Sci-Fi");

        // Step 2: Link the book to the category
        BookCategory link = new BookCategory(bookId, categoryId);
        bookCategoryDAO.insert(link);

        // Step 3: Read all links for this book
        LiveData<List<BookCategory>> linksObserver =
                bookCategoryDAO.getCategoriesForBook(bookId);
        List<BookCategory> links = LiveDataTestUtil.getOrAwaitValue(linksObserver);

        // Step 4: Check there is one link with matching ids
        assertEquals(1, links.size());
        BookCategory saved = links.get(0);
        assertEquals(bookId, saved.getBookID());
        assertEquals(categoryId, saved.getCategoryID());
    }

    /**
     * Test 2:
     * Updates the category id for a link and checks if the new id shows up.
     */
    @Test
    public void updateBookCategory_updatesCategoryIdForBook() throws InterruptedException {
        // Step 1: Prepare user, book, and two categories
        int userId = insertMontyUserAndGetId();
        int bookId = insertBookForUser(userId);
        int sciFiId = insertCategoryAndGetId("Sci-Fi");
        int fantasyId = insertCategoryAndGetId("Fantasy");

        // Step 2: Link the book to the Sci-Fi category first
        BookCategory link = new BookCategory(bookId, sciFiId);
        bookCategoryDAO.insert(link);

        // Step 3: Read the link row
        LiveData<List<BookCategory>> linksObserver =
                bookCategoryDAO.getCategoriesForBook(bookId);
        List<BookCategory> links = LiveDataTestUtil.getOrAwaitValue(linksObserver);
        BookCategory saved = links.get(0);

        // Step 4: Change the category id to Fantasy and update the row
        saved.setCategoryID(fantasyId);
        bookCategoryDAO.update(saved);

        // Step 5: Read again and check the new category id
        linksObserver = bookCategoryDAO.getCategoriesForBook(bookId);
        List<BookCategory> updatedLinks = LiveDataTestUtil.getOrAwaitValue(linksObserver);
        BookCategory updated = updatedLinks.get(0);

        assertEquals(fantasyId, updated.getCategoryID());
    }

    /**
     * Test 3:
     * Deletes the link row and checks if the list becomes empty.
     */
    @Test
    public void deleteBookCategory_removesLink() throws InterruptedException {
        // Step 1: Prepare user, book, and category
        int userId = insertMontyUserAndGetId();
        int bookId = insertBookForUser(userId);
        int categoryId = insertCategoryAndGetId("Sci-Fi");

        // Step 2: Insert the link row
        BookCategory link = new BookCategory(bookId, categoryId);
        bookCategoryDAO.insert(link);

        // Step 3: Make sure one link exists
        LiveData<List<BookCategory>> linksObserver =
                bookCategoryDAO.getCategoriesForBook(bookId);
        List<BookCategory> links = LiveDataTestUtil.getOrAwaitValue(linksObserver);
        assertEquals(1, links.size());

        // Step 4: Delete the link row
        BookCategory saved = links.get(0);
        bookCategoryDAO.delete(saved);

        // Step 5: Read again and check if list is empty
        linksObserver = bookCategoryDAO.getCategoriesForBook(bookId);
        List<BookCategory> afterDelete = LiveDataTestUtil.getOrAwaitValue(linksObserver);
        assertTrue(afterDelete.isEmpty());
    }
}
