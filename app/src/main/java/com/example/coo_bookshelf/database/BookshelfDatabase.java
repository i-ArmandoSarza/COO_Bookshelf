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
import com.example.coo_bookshelf.database.DAO.CategoryDAO;
import com.example.coo_bookshelf.database.DAO.BookCategoryDAO;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.BookCategory;
import com.example.coo_bookshelf.database.entities.Category;
import com.example.coo_bookshelf.database.entities.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Book.class, Category.class,
    BookCategory.class}, version = 3, exportSchema = false)
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
                insert into User (Email, password, firstName, lastName, isAdmin)
                values ('monty@csumb.edu', 'admin1', 'Monty', 'Rey', 1);
              """.stripIndent()
      );
      //  Red Rising Book seed
      db.execSQL(
          """
                insert into Book(UserId, Title, Author, AuthorKey, FirstPublishedYear, WorksID, imageUrl, isbn, Description)
                values (last_insert_rowId(), 'Red Rising', 'Pierce Brown', 'OL7621609A', 'October 14, 2025'
                  , 'OL17076473W'
                  , 'https://covers.openlibrary.org/b/isbn/9780593871522-L.jpg'
                  , '0593871529'
                  , 'I live for the dream that my children will be born free, she says. "That they will be what they like. That they will own the land their father gave them.
              
                     "I live for you," I say sadly.
              
                     Eo kisses my cheek. "Then you must live for more."
              
                     Darrow is a Red, a member of the lowest caste in the color-coded society of the future. Like his fellow Reds, he works all day, believing that he and his people are
                     making the surface of Mars livable for future generations.
              
                     Yet he spends his life willingly, knowing that his blood and sweat will one day result in a better world for his children.
              
                     But Darrow and his kind have been betrayed. Soon he discovers that humanity already reached the surface generations ago. Vast cities and sprawling parks spread across the planet. 
                     Darrow—and Reds like him—are nothing more than slaves to a decadent ruling class.
              
                     Inspired by a longing for justice, and driven by the memory of lost love, Darrow sacrifices everything to infiltrate the legendary Institute, a proving ground for the 
                     dominant Gold caste, where the next generation of humanitys overlords struggle for power. He will be forced to compete for his life and the very future of civilization 
                     against the best and most brutal of Societys ruling class. There, he will stop at nothing to bring down his enemies... even if it means he has to become one of them to do so.');
              """.stripIndent()
      );
      // Kit Runner book seed
      db.execSQL(
          """
                insert into Book(UserId, Title, Author, AuthorKey, FirstPublishedYear, WorksID, imageUrl, isbn, Description)
                select userid, 'The Kite Runner', 'Khaled Hosseini', 'OL1412764A', 'October 14, 2025'
                  , 'OL5781992W'
                  , 'https://covers.openlibrary.org/b/isbn/1417640391-L.jpg'
                  , '1417640391'
                  , 'The unforgettable, heartbreaking story of the unlikely friendship between a wealthy boy and the son of his father’s servant, The Kite Runner is a beautifully crafted novel set in a country that is in the process of being destroyed. It is about the power of reading, the price of betrayal, and the possibility of redemption; and an exploration of the power of fathers over sons—their love, their sacrifices, their lies.
              
                     A sweeping story of family, love, and friendship told against the devastating backdrop of the history of Afghanistan over the last thirty years, The Kite Runner is an unusual and powerful novel that has become a beloved, one-of-a-kind classic.'
                from user
                where email = 'monty@csumb.edu'
              """.stripIndent()
      );
      // Artemis book seed
      db.execSQL(
          """
                insert into Book(UserId, Title, Author, AuthorKey, FirstPublishedYear, WorksID, imageUrl, isbn, Description)
                select userid, 'Artemis', 'Andy Weir', 'OL7234434A', '2017'
                  , 'OL17837968W'
                  , 'https://covers.openlibrary.org/b/id/12639918-L.jpg'
                  , '9780091956950'
                  , 'Not crazy, eccentric-billionaire rich, like many of the visitors to her hometown of Artemis, humanity''s first and only lunar colony. Just rich enough to move out of her coffin-sized apartment and eat something better than flavored algae. Rich enough to pay off a debt she''s owed for a long time.
              
                     So when a chance at a huge score finally comes her way, Jazz can''t say no. Sure, it requires her to graduate from small-time smuggler to full-on criminal mastermind. And it calls for a particular combination of cunning, technical skills, and large explosions--not to mention sheer brazen swagger. But Jazz has never run into a challenge her intellect can''t handle, and she figures she''s got the "swagger" part down.
              
                     The trouble is, engineering the perfect crime is just the start of Jazz''s problems. Because her little heist is about to land her in the middle of a conspiracy for control of Artemis itself.
              
                     Trapped between competing forces, pursued by a killer and the law alike, even Jazz has to admit she''s in way over her head. She''ll have to hatch a truly spectacular scheme to have a chance at staying alive and saving her city.
              
                     Jazz is no hero, but she is a very good criminal.
              
                     That''ll have to do.
              
                     Propelled by its heroine''s wisecracking voice, set in a city that''s at once stunningly imagined and intimately familiar, and brimming over with clever problem solving and heisty fun, Artemis is another irresistible brew of science, suspense, and humor from #1 bestselling author Andy Weir.
              
                     This description comes from the publisher.
                  '
                from user
                where email = 'monty@csumb.edu'
              """.stripIndent()
      );



      // Create user Katrina and books record with raw sql.
      Log.i(MainActivity.TAG, "Creating user Katrina and books record.");
      db.execSQL(
          """
                insert into User (Email, password, firstName, lastName, isAdmin)
                values ('kat@csumb.edu', 'password1', 'Katrina', 'Jones', 0);
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
        userMonty.setFirstName("Monty");
        userMonty.setLastName("Rey");
        userDAO.insert(userMonty);

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Monty's Book record.");
        // add monty's book info
        var montyUserId = userDAO.getUserIdByUserEmailSync("monty@csumb.edu");
        Book montyBook = new Book(montyUserId, "Red Rising",
            "Pierce Brown", "OL7621609A", "OL17076473W");
        montyBook.setFirstPublishedYear("October 14, 2025");
        bookDAO.insert(montyBook);

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Katrina's User record.");
        // Create seed record for Katrina non admin user
        User kat = new User("kat@csumb.edu", "password1");
        kat.setFirstName("Katrina");
        kat.setLastName("Jones");
        kat.setAdmin(false);
        userDAO.insert(kat);

        Log.i(MainActivity.TAG, "onDestructiveMigration: Creating Katrina's Book record.");
        // Creating seed record for Katrina's book.
        var katUserId = userDAO.getUserIdByUserEmailSync("kat@csumb.edu");
        Book katBook = new Book(katUserId, "The Kite Runner",
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

  public abstract CategoryDAO categoryDAO();

  public abstract BookCategoryDAO bookCategoryDAO();

}
