package com.example.coo_bookshelf;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.database.entities.User;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
  private BookshelfRepository repository;
  private BookshelfDatabase db;

  @Before
  public void createDb() {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, BookshelfDatabase.class).build();

  }

  @After
  public void closeDb() throws IOException {
    db.close();
  }

  @Test
  public void writeUserAndReadInList() throws Exception {
    User userMonty = new User("monty@csumb.edu", "admin1");
    userMonty.setAdmin(true);
    userMonty.setFirstName("Monty");
    userMonty.setLastName("Rey");
    repository.insert(userMonty);

    User montyResp = repository.getUserByUserEmail("monty@csumb.edu").getValue();
    assertThat(montyResp, equalTo(userMonty));
  }
}
