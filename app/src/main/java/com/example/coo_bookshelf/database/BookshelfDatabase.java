package com.example.coo_bookshelf.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.coo_bookshelf.MainActivity;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.BookCategory;
import com.example.coo_bookshelf.database.entities.Category;
import com.example.coo_bookshelf.database.entities.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Book.class, Category.class,
    BookCategory.class}, version = 3, exportSchema = false)
public abstract class BookshelfDatabase extends RoomDatabase {

  public static final String USER_TABLE = "User";
  public static final String BOOK_TABLE = "Book";
  public static final String CATEGORY_TABLE = "Categories";
  public static final String BOOK_CATEGORY_TABLE = "BookCategories";
  private final static String DATABASE_NAME = "BookshelfDatabase";
  private static final int NUMBER_OF_THREADS = 4;

  // will only have a max of 4 threads in the pool
  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(
      NUMBER_OF_THREADS);

  private static volatile BookshelfDatabase INSTANCE;
  private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      // Set up default accounts
      Log.i(MainActivity.TAG, "DATABASE Created!");
      databaseWriteExecutor.execute(() -> {
        UserDAO userDAO = INSTANCE.userDAO();
        userDAO.deleteAll();
        User admin = new User("admin1", "admin1");
        admin.setAdmin(true);
        admin.setFirstName("Monty Rey");
        userDAO.insert(admin);

        User testUser1 = new User("testuser1", "testuser1");
        testUser1.setFirstName("Katrina");
        userDAO.insert(testUser1);
      });
    }
  };
  static BookshelfDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (BookshelfDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
                  context.getApplicationContext(),
                  BookshelfDatabase.class,
                  DATABASE_NAME
              )
              .fallbackToDestructiveMigration(true)
              .addCallback(addDefaultValues)
              .build();
        }
      }
    }
    return INSTANCE;
  }

  //TODO: Add DOA here.
  public abstract UserDAO userDAO();

}
