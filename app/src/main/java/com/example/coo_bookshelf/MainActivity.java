package com.example.coo_bookshelf;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
  private static BookshelfRepository repository;
  private ActivityMainBinding binding;
  public static final String TAG = "CCO_Bookshelf";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    Log.d(TAG, "onCreate");
    repository = BookshelfRepository.getRepository(getApplication());

    // This is for testing only to verify that the we can retrieve a user from the database.
    var email = "monty@csumb.edu";
    var userObserver = repository.getUserByUserEmail(email);
    userObserver.observe(this, user -> {
      if (user != null) {
        String password = user.getPassword();
        var testMsg =
            String.format(
                "onCreate: Testing DB access. Retrieved monty record.%nUser first name: '%s' and email is '%s'"
                , user.getFirstName()
                , user.getEmail());
        var shortMsg = String.format("Name: '%s' and email is '%s'", user.getFirstName(), user.getEmail());
        toastMaker(shortMsg);
        Log.i(TAG, testMsg);

      } else {
        Log.i(TAG, "onCreate: Failed to get user");
        toastMaker(String.format("User %s is not a valid username", email));
      }
    });
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}