package com.example.coo_bookshelf;

import static android.view.View.GONE;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import com.example.coo_bookshelf.booksearch.MyBookSearchActivity;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityMainBinding;
import com.example.coo_bookshelf.mybooks.MyBookActivity;
import com.example.coo_bookshelf.admin.AdminSettings;


public class MainActivity extends AppCompatActivity {

  public static final String TAG = "CCO_Bookshelf";
  static final String USER_ID = "com.example.coo_bookshelf.USER_ID";
  private static BookshelfRepository repository;
  private ActivityMainBinding binding;
  // Variable to hold logged in user ID. -1 means no user is logged in.
  int loggedInUserId = -1;

  // Notification
  private static final int REQ_POST_NOTIFICATIONS = 100;

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
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    repository = BookshelfRepository.getRepository(getApplication());

    userLogin();

    // Creates an intent to start LoginPageActivity if no user is logged in.
    if (loggedInUserId == -1) {
      Intent intent = LoginPageActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
      finish();
      return;
    }
    welcomeScreen();

    // Menu Button
    setSupportActionBar(binding.toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // Notification
    createNotificationChannel();
    askNotificationPermission();

    // View my books onClick
    binding.MyBooksButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = MyBookActivity.MyBookActivityIntentFactory(getApplicationContext(),
            loggedInUserId);
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
        Intent intent = MyBookSearchActivity.MyBookSearchActivityIntentFactory(
            getApplicationContext(), loggedInUserId);
        startActivity(intent);
      }
    });
  }

  private void userLogin() {
    loggedInUserId = getIntent().getIntExtra(USER_ID, -1);
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  //=====================
  //  Main Menu Options
  //=====================
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();

    // About Text -> About page
    if (item.getItemId() == R.id.menu_about) {
      Intent intent = new Intent(this, AboutActivity.class);
      startActivity(intent);
      return true;
    }

    // Sign Out Text --> Login page
    if (item.getItemId() == R.id.menu_signout) {
      // Go back to login screen
      startActivity(LoginPageActivity.loginIntentFactory(this));
      finish();
      return true;
    }

    // Notification -> trigger notification
    if (item.getItemId() == R.id.menu_notify) {
      Intent intent = new Intent(this, ReminderReceiver.class);
      intent.setAction("BOOKSHELF_REMINDER");
      sendBroadcast(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  //=====================
  //    Welcome Screen
  //=====================
  private void welcomeScreen() {
    //modified over from LoginPageActivity.verifyUser() method
    var userLiveData = repository.getUserByUserId(loggedInUserId);
    userLiveData.observe(this, user -> {
      // Stop observing after first result so we don't get repeated callbacks
      userLiveData.removeObservers(this);

      //shouldn't happen but just in case
      if (user == null) {
        binding.WelcomeTitleTextView.setText("Welcome!");
        return;
      }

      // If user is not admin, hide admin button and text
      if (!user.isAdmin()) {
        binding.IsAdminLandingPageTextView.setText("");
        binding.AdminButton.setVisibility(GONE);
      }

      String name = user.getFirstName();
      if (name != null && !name.trim().isEmpty()) {
        binding.WelcomeTitleTextView.setText("Welcome, " + name + "!");
      } else {
        // No first name set, generic welcome
        binding.WelcomeTitleTextView.setText("Welcome!");
      }

    });

    //setting number of books welcome
    var bookLiveData = repository.getBookCountByUserId(loggedInUserId);
    bookLiveData.observe(this, count -> {

      if (count == null || count == 0) {
        binding.LibrarySizeTextView.setText("Search for a book to start adding!");
      } else if (count == 1) {
        binding.LibrarySizeTextView.setText("You currently have 1 book in your bookshelf!");
      }else{
        binding.LibrarySizeTextView.setText("You currently have " + count + " books in your bookshelf!");
      }
    });
  }

  //=====================
  //   Notification
  //=====================

  // Create a notification channel
  private void createNotificationChannel() {
    NotificationChannel channel = new NotificationChannel(
        "default",           // ID must match ReminderReceiver
        "BookshelfChannel",     // Name shown in system settings
        NotificationManager.IMPORTANCE_DEFAULT
    );
    // Description
    channel.setDescription("Bookshelf Notifications");

    NotificationManager manager = getSystemService(NotificationManager.class);
    manager.createNotificationChannel(channel);   // Create the channel
  }

  // Ask user for notification permission
  private void askNotificationPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED) {

      // Request the permission if not granted
      ActivityCompat.requestPermissions(
          this,
          new String[]{Manifest.permission.POST_NOTIFICATIONS},
          REQ_POST_NOTIFICATIONS
      );
    }
  }

}

