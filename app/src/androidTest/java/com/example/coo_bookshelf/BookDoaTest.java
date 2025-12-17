package com.example.coo_bookshelf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.DAO.BookDAO;
import com.example.coo_bookshelf.database.DAO.UserDAO;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class BookDoaTest {
 @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
  private BookshelfDatabase db;
  private BookDAO bookDAO;
  private UserDAO userDAO;

  @Before
  public void createDb() {
    // Using in memory database for testing so data is not saved to disk
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, BookshelfDatabase.class)
        .allowMainThreadQueries()
        .build();

    bookDAO = db.bookDAO();
    userDAO = db.userDAO();
  }

  @After
  public void closeDb() {
    db.close();
  }

  @Before
  public void setup() {
    db.clearAllTables();
  }


  @Test
  public void addBook_returnsCorrectBookForTheUser() throws InterruptedException {
    // setup monty user
    // Creating Monty seed created.
    User userMonty = new User("monty@csumb.edu", "admin1");
    userMonty.setAdmin(true);
    userMonty.setFirstName("Monty");
    userMonty.setLastName("Rey");
    userDAO.insert(userMonty);

    var montyUserId = userDAO.getUserIdByUserEmailSync("monty@csumb.edu");
    assertTrue("Monty user id should be > 0", montyUserId > 0);

    // Add a book to monty's account.
    Book montyBook = new Book(montyUserId, "Red Rising",
        "Pierce Brown", "OL7621609A", "OL17076473W");
    montyBook.setFirstPublishedYear("October 14, 2025");
    bookDAO.insert(montyBook);

    // Get list of books.
    LiveData<List<Book>> booksObserver = bookDAO.getBooksByUserId(montyUserId);

    // Use LiveDataTestUtil.getOrAwaitValue() access the LiveData in the db test.
    var books =  LiveDataTestUtil.getOrAwaitValue(booksObserver);
    boolean hasRedRising = books.stream().anyMatch(book -> book.getTitle().equals("Red Rising"));
    assertTrue("Book list should contain 'Red Rising'", hasRedRising);
  }

  @Test
  public void removeBook_returnsEmptyList() throws InterruptedException {
    // setup monty user
    // Creating Monty seed created.
    User userMonty = new User("monty@csumb.edu", "admin1");
    userMonty.setAdmin(true);
    userMonty.setFirstName("Monty");
    userMonty.setLastName("Rey");
    userDAO.insert(userMonty);

    // Get MontyUserID
    var montyUserId = userDAO.getUserIdByUserEmailSync("monty@csumb.edu");
    assertTrue("Monty user id should be > 0", montyUserId > 0);

    // Add a book to monty's account.
    Book montyBook = new Book(montyUserId, "Red Rising",
        "Pierce Brown", "OL7621609A", "OL17076473W");
    montyBook.setFirstPublishedYear("October 14, 2025");
    bookDAO.insert(montyBook);

    // Get list of books.
    LiveData<List<Book>> booksObserver = bookDAO.getBooksByUserId(montyUserId);

    // Use LiveDataTestUtil.getOrAwaitValue() access the LiveData in the db test.
    var books =  LiveDataTestUtil.getOrAwaitValue(booksObserver);
    Book book = books.stream().filter(b -> b.getTitle().equals("Red Rising")).findFirst().get();
    // check we have the book.
    assertEquals("Book list should contain 'Red Rising'", "Red Rising", book.getTitle());

    // Delete the book.
    bookDAO.delete(book);

    // Get list of books after deleting the Red Rising title. Should be empty.
    booksObserver = bookDAO.getBooksByUserId(montyUserId);
    var deletedBooks =  LiveDataTestUtil.getOrAwaitValue(booksObserver);
    assertEquals(0, deletedBooks.size());
  }

    /**
     * Tests updating a book entity changes the title in the database.
     *  - Inserts a user and a book linked to that user.
     *  - Reads book from database and updates title field.
     *  - Calls BookDAO update method.
     *  - Reads book again and verifies new title is stored.
     */
  @Test
    public void updateBook_updatesTitleForExistingBook() throws InterruptedException {
      // setup monty user
      User userMonty = new User ("montyUpdate@csumb.edu", "admin1");
      userMonty.setAdmin(true);
      userMonty.setFirstName("Monty");
      userMonty.setLastName("Rey");
      userDAO.insert(userMonty);

      // Get Monty user ID
      int montyUserId = userDAO.getUserIdByUserEmailSync("montyUpdate@csumb.edu");
        assertTrue("Monty user id should be > 0", montyUserId > 0);

      // Add a book to monty's account.
      Book montyBook = new Book(
              montyUserId,
              "Red Rising",
                "Pierce Brown",
                "OL7621609A",
                    "OL17076473W"
        );
      montyBook.setFirstPublishedYear("October 14, 2025");
      bookDAO.insert(montyBook);

      // Read the book back
      LiveData<List<Book>> booksObserver = bookDAO.getBooksByUserId(montyUserId);
      List<Book> books = LiveDataTestUtil.getOrAwaitValue(booksObserver);

      assertEquals(1, books.size());
      Book book = books.get(0);
      assertEquals("Red Rising", book.getTitle());

      // Update the title
      book.setTitle("Red Rising (Updated)");
      bookDAO.update(book);

      // Read again and verify the update
      LiveData<List<Book>> updatedObserver = bookDAO.getBooksByUserId(montyUserId);
      List<Book> updatedBooks = LiveDataTestUtil.getOrAwaitValue(updatedObserver);
      Book updatedBook = updatedBooks.get(0);

      assertEquals("Book title should be updated",
              "Red Rising (Updated)",
              updatedBook.getTitle());
  }
}
