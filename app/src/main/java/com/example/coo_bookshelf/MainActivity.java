package com.example.coo_bookshelf;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coo_bookshelf.booksearch.MyBookSearchActivity;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityMainBinding;
import com.example.coo_bookshelf.mybooks.MyBookActivity;


public class MainActivity extends AppCompatActivity {

  public static final String TAG = "CCO_Bookshelf";
  private static final String USER_ID = "com.example.coo_bookshelf.USER_ID";
  private static BookshelfRepository repository;
  private ActivityMainBinding binding;
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
    //TODO: If doing recycle view, refactor, might just have to make a work around
    /*Not the best source but it does do it how this was previously set up.
    https://www.tutorialspoint.com/how-can-i-remove-a-button-or-make-it-invisible-in-android
    p.s. I think doing it this way would fix the database not populating until you make a call.
    since it only became an issue after doing it this way.
    */
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    repository = BookshelfRepository.getRepository(getApplication());

    userLogin();

    // Creates an intent to start LoginPageActivity if no user is logged in.
    if (loggedInUserId == -1) {
      Intent intent = LoginPageActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    }
    welcomeScreen();

    // Menu Button
    setSupportActionBar(binding.toolbar);
    if(getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // View my books onClick
    binding.MyBooksButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = MyBookActivity.MyBookActivityIntentFactory(getApplicationContext(), loggedInUserId);
        startActivity(intent);
      }
    });

    // When the "Admin Settings" button is clicked on the login screen
    binding.AdminButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Make a plan to go to the SignUpActivity
        Intent intent = AdminSettings.AdminSettingsIntentFactory(getApplicationContext());
        startActivity(intent);
      }
    });

    // Search for books onClick
    binding.SearchBooksButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = MyBookSearchActivity.MyBookSearchActivityIntentFactory(getApplicationContext(), loggedInUserId);
        startActivity(intent);
      }
    });
  }

  private void userLogin() {
    //TODO: Create login method
    //TODO: Create a logout button
    loggedInUserId = getIntent().getIntExtra(USER_ID, -1);
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  //============================================
  //    Main Menu Options --> About |  Sign Out
  //============================================
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();

    // About Text -> About page
    if(item.getItemId() == R.id.menu_about) {
      Intent intent = new Intent(this, AboutActivity.class);
      startActivity(intent);
      return true;
    }

    // Sign Out Text --> Login page
    if(item.getItemId() == R.id.menu_signout) {
      // Go back to login screen
      startActivity(LoginPageActivity.loginIntentFactory(this));
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void welcomeScreen() {
    //modified over from LoginPageActivity.verifyUser() method
    var userLiveData = repository.getUserByUserId(loggedInUserId);
    userLiveData.observe(this, user -> {
      // Stop observing after first result so we don't get repeated callbacks
      userLiveData.removeObservers(this);

      //shouldn't happen but just in case
      if (user != null) {

        if(!user.isAdmin()){
          binding.IsAdminLandingPageTextView.setText("");
          binding.AdminButton.setVisibility(GONE);
        }

        String name = user.getFirstName();
        String welcomeMessage = "Welcome " + name + "!";
        binding.WelcomeTitleTextView.setText(welcomeMessage);

      }
    });
  }
}

