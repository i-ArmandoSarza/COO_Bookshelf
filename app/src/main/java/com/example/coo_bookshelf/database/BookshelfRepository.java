package com.example.coo_bookshelf.database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.coo_bookshelf.MainActivity;
import com.example.coo_bookshelf.database.DAO.BookDAO;
import com.example.coo_bookshelf.database.DAO.UserDAO;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.User;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookshelfRepository {
  private final UserDAO userDAO;
  private final BookDAO bookDAO;
  private static BookshelfRepository repository;


  private BookshelfRepository(Application application) {
    BookshelfDatabase db = BookshelfDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
    this.bookDAO = db.bookDAO();
  }

  public static BookshelfRepository getRepository(Application application) {
    if(repository != null) {
      return repository;
    }
    Future<BookshelfRepository> future = BookshelfDatabase.databaseWriteExecutor.submit(
        new Callable<BookshelfRepository>() {
          @Override
          public BookshelfRepository call() throws Exception {
            return new BookshelfRepository(application);
          }
        }
    );
    try {
      repository = future.get();
      return repository;
    }catch (InterruptedException | ExecutionException e) {
     Log.e(MainActivity.TAG, "Problem getting GymLogRepository, thread error.", e);
    }
    return null;
  }

  //==========================================
  //      USER METHODS
  //==========================================
  public void insert(User user) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.insert(user);
    });
  }

  public void delete(User user) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.delete(user);
    });
  }

  public void update(User user) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.update(user);
    });
  }

  public void deleteAllUsers() {
    BookshelfDatabase.databaseWriteExecutor.execute(userDAO::deleteAll);
  }

  public LiveData<User> getUserByUserId(int userId) {
    return userDAO.getUserByUserId(userId);
  }

  //setting up recycler view for user data
  public LiveData<List<User>> getAllUsersLiveData() {
    return userDAO.getAllUsersLiveData();
  }

  public LiveData<User> getUserByUserEmail(String username) {
    return userDAO.getUserByUserEmail(username);
  }

  public void updateUserName(int userId, String firstName, String lastName) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      userDAO.updateUserName(userId, firstName, lastName);
    });
  }

  //==========================================
  //      SYNC METHODS
  //==========================================
  public int getUserByUserIdSync(int userId) {
    try {
      return BookshelfDatabase.databaseWriteExecutor
              .submit(() -> userDAO.getUserByUserIdSync(userId))
              .get();   // wait for result
    } catch (Exception e) {
      e.printStackTrace();
      return -1;         // -1 for "not found/error"
    }
  }
  public int getUserIdByUserEmailSync(String email) {
    try {
      return BookshelfDatabase.databaseWriteExecutor
              .submit(() -> userDAO.getUserIdByUserEmailSync(email))
              .get();
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }
  //==========================================
  //      BOOK METHODS
  //==========================================
  public void insert(Book book) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      bookDAO.insert(book);
    });
  }

  public void update(Book...books) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      bookDAO.update(books);
    });
  }

  public void delete(Book...books) {
    BookshelfDatabase.databaseWriteExecutor.execute(() -> {
      bookDAO.delete(books);
    });
  }

  // https://developer.android.com/topic/libraries/architecture/livedata
  // The docs say we should use live data in the repo.
  public LiveData<List<Book>> getBooksByUserId(int userId) {
    return bookDAO.getBooksByUserId(userId);
  }

  public LiveData<Integer>getBookCountByUserId(int userId){
    return bookDAO.getBookCountByUserId(userId);
  }

  public LiveData<Book> getBookById(int bookId) {
    return bookDAO.getBookById(bookId);
  }

  public void deleteAllBooks() {
    BookshelfDatabase.databaseWriteExecutor.execute(bookDAO::deleteAll);
  }

}
