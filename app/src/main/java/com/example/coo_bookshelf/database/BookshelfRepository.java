package com.example.coo_bookshelf.database;

import android.app.Application;
import android.util.Log;
import com.example.coo_bookshelf.MainActivity;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookshelfRepository {
  private final UserDAO userDAO;
  private static BookshelfRepository repository;


  private BookshelfRepository(Application application) {
    BookshelfDatabase db = BookshelfDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
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

  // TODO: Implement all user queries.

}
