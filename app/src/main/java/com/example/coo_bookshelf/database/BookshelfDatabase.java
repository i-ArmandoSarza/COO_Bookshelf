package com.example.coo_bookshelf.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.coo_bookshelf.MainActivity;
import com.example.coo_bookshelf.database.DAO.BookDAO;
import com.example.coo_bookshelf.database.DAO.UserDAO;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.BookCategory;
import com.example.coo_bookshelf.database.entities.Category;
import com.example.coo_bookshelf.database.entities.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Book.class, Category.class,
    BookCategory.class}, version = 1, exportSchema = false)
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
      // This only runs the first time and the database is created.
      Log.i(MainActivity.TAG,
          "DATABASE Creating users!"); // TODO: Remove this. This is for testing purposes.

      Log.i(MainActivity.TAG, "Creating user Monty!");

      db.execSQL(
          """
                insert into User (Email, password, firstName, isAdmin)
                values ('monty@csumb.edu', 'admin1', 'Monty Rey', 1);
              """.stripIndent()
      );
      db.execSQL(
          """
                insert into Book(UserId, Title, Author, AuthorKey, FirstPublishedYear, WorksID)
                values (last_insert_rowId(), 'Red Rising', 'Pierce Brown', 'OL7621609A', 'October 14, 2025', 'OL17076473W');
              """.stripIndent()
      );

      // Create user Katrina and books record with raw sql.
      Log.i(MainActivity.TAG, "Creating user Katrina and books record.");
      db.execSQL(
          """
                insert into User (Email, password, firstName, isAdmin)
                values ('katcsumb', 'katcsumb', 'Katrina', 0);
              """.stripIndent()
      );
      db.execSQL(
          """
                insert into Book(UserId, Title, Author, AuthorKey, FirstPublishedYear, WorksID)
                values (last_insert_rowId(), 'Red Rising', 'Pierce Brown', 'OL7621609A', 'October 14, 2025', 'OL17076473W');
              """.stripIndent()
      );
    }

    // onDestructiveMigration only runs when the database version is upgraded, and will reseed data.
    @Override
    public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
      super.onDestructiveMigration(db);
      databaseWriteExecutor.execute(() -> {
        Log.i(MainActivity.TAG, "onDestructiveMigration: Re-seeding database.");
        // DAO setup
        UserDAO userDAO = INSTANCE.userDAO();
        BookDAO bookDAO = INSTANCE.bookDAO();

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Monty's User record.");
        // Creating Monty seed created.
        User userMonty = new User("monty@csumb.edu", "admin1");
        userMonty.setAdmin(true);
        userMonty.setFirstName("Monty Rey");
        userDAO.insert(userMonty);

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Monty's Book record.");
        // add monty's book info
        var montyUserId = userDAO.getUserByUserEmailSync("monty@csumb.edu");
        Book montyBook = new Book(montyUserId, "Red Rising",
            "Pierce Brown", "OL7621609A", "OL17076473W");
        montyBook.setFirstPublishedYear("October 14, 2025");
        bookDAO.insert(montyBook);

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Katrina's User record.");
        // Create seed record for Katrina non admin user
        User kat = new User("kat@csumb.edu", "password1");
        kat.setFirstName("Katrina");
        kat.setAdmin(false);
        userDAO.insert(kat);

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Katrina's Book record.");
        // Creating seed record for Katrina's book.
        var katUserId = userDAO.getUserByUserEmailSync("kat@csumb.edu");
        Book katBook = new Book(montyUserId, "The Kite Runner",
            "Khaled Hossein", "OL1412764A", "OL15902631W");
        katBook.setFirstPublishedYear("2004");
        bookDAO.insert(katBook);
      });
    }
  };

  public static BookshelfDatabase getDatabase(final Context context) {
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
          Log.i(MainActivity.TAG,
              "getDatabase synchronized"); // TODO: Remove this. This is for testing purposes.
        }
      }
    }
    Log.i(MainActivity.TAG, "getDatabase returning instance");
    return INSTANCE;
  }

  //TODO: Add DOA's here.
  public abstract UserDAO userDAO();

  public abstract BookDAO bookDAO();

}
