package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityLoginPageBinding;

public class LoginPageActivity extends AppCompatActivity {

    // Through binding, we can access all views in activity_login_page.xml.
    private ActivityLoginPageBinding binding;
  private static BookshelfRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate creates the screen from the XML layout.
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        // Shows the layout on the screen
        setContentView(binding.getRoot());
        repository = BookshelfRepository.getRepository(getApplication());

        // Action for login button when clicked.
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a plan to go to MainActivity after login.
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(),
                        0);

                // TODO: Update VerifyUser to get the actually users.
                // Currently this only verifies a hardcoded users exist.
                verifyUser();
                startActivity(intent);
            }
        });

        // When the "Sign Up" text is clicked on the login screen
        binding.signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a plan to go to the SignUpActivity
                Intent intent = SignUpActivity.signUpIntentFactory(LoginPageActivity.this);
                startActivity(intent);
            }
        });

    }

    /** Lets us jump to this activity from other activities */
    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginPageActivity.class);
    }

    private void verifyUser(){
      // This has not been wired up to check the loginActivity inputs.
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
          Log.i(MainActivity.TAG, testMsg);

        } else {
          Log.i(MainActivity.TAG, "onCreate: Failed to get user");
          toastMaker(String.format("User %s is not a valid username", email));
        }
      });

    }
    private void toastMaker(String message) {
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}