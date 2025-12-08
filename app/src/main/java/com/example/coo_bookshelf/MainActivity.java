package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coo_bookshelf.database.BookshelfRepository;


public class MainActivity extends AppCompatActivity {

  public static final String TAG = "CCO_Bookshelf";
  private static final String USER_ID = "com.example.coo_bookshelf.USER_ID";
  private static BookshelfRepository repository;
  // Variable to hold logged in user ID. -1 means no user is logged in.
  int loggedInUserId = -1;

  // Method makes an intent to start MainActivity.
  static Intent mainActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MainActivity.class);
    // Puts the user ID into the intent to pass to MainActivity.
    intent.putExtra(USER_ID, userId);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    repository = BookshelfRepository.getRepository(getApplication());

    userLogin();

    // Creates an intent to start LoginPageActivity if no user is logged in.
    if (loggedInUserId == -1) {
      Intent intent = LoginPageActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    }


  }

  private void userLogin() {
    //TODO: Create login method
    loggedInUserId = getIntent().getIntExtra(USER_ID, -1);
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

}