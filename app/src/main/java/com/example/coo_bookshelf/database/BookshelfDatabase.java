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
    BookCategory.class}, version = 14, exportSchema = false)
public abstract class BookshelfDatabase extends RoomDatabase {

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
      Log.i(MainActivity.TAG, "DATABASE Creating users!"); // TODO: Remove this. This is for testing purposes.
      databaseWriteExecutor.execute(() -> {
        UserDAO userDAO = INSTANCE.userDAO();
        userDAO.deleteAll();
        User admin = new User("monty@csumb.edu", "admin1");
        admin.setAdmin(true);
        admin.setFirstName("Monty Rey");
        userDAO.insert(admin);
        Log.i(MainActivity.TAG, "Creating user Monty!");

//        User testUser1 = new User("testuser1", "testuser1");
//        testUser1.setFirstName("Katrina");
//        userDAO.insert(testUser1);
        Log.i(MainActivity.TAG, "Creating user Katrina and books record.");
        db.execSQL(
        """
          insert into User (Email, password, firstName, isAdmin)
          values ('katcsumb', 'katcsumb', 'Katrina', 0);
        """.stripIndent());
        db.execSQL(
      """
          insert into Book(UserId, Title, Author, AuthorKey, FirstPublishedYear, WorksID)
          values (last_insert_rowid(), 'Red Rising', 'Pierce Brown', 'OL7621609A', 'October 14, 2025', 'OL17076473W');
         """.stripIndent());
      });
    }
  };
  static BookshelfDatabase getDatabase(final Context context) {
    Log.i(MainActivity.TAG, "getDatabase");
    if (INSTANCE == null) {
      synchronized (BookshelfDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
                  context.getApplicationContext(),
                  BookshelfDatabase.class,
                  DbConfig.DATABASE_NAME
              )
              .fallbackToDestructiveMigration(true)
              .addCallback(addDefaultValues)
              .build();
          Log.i(MainActivity.TAG, "getDatabase synchronized"); // TODO: Remove this. This is for testing purposes.
        }
      }
    }
    Log.i(MainActivity.TAG, "getDatabase returning instance");
    return INSTANCE;
  }

  //TODO: Add DOA here.
  public abstract UserDAO userDAO();

}
