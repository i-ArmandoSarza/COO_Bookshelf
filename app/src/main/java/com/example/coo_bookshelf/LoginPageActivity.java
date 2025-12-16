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
                // Verify user credentials here
                // If login is valid, verifyUser() will navigate to the main activity
                verifyUser();
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

    private void verifyUser() {
        // Read user input
        String email = binding.emailLoginEditText.getText().toString().trim();
        String passwordInput = binding.passwordLoginEditText.getText().toString().trim();

        // Check if input is empty
        if (email.isEmpty() || passwordInput.isEmpty()) {
            toastMaker("Please enter both email and password.");
            return;
        }

        // Safety check incase repository failed to initialize
        if (repository == null) {
            toastMaker("Error connecting to database. Try again.");
            Log.e(MainActivity.TAG, "Repository is null in LoginPageActivity.verifyUser()");
            return;
        }

        // Retrieve user with this email from repository
        var userLiveData = repository.getUserByUserEmail(email);
        userLiveData.observe(this, user -> {
            // Stop observing after first result so we don't get repeated callbacks
            userLiveData.removeObservers(this);

            if (user != null) {
                // User found, check password
                if (user.getPassword().equals(passwordInput)) {
                    // Password matches, navigate to MainActivity
                    Intent intent = MainActivity.mainActivityIntentFactory(
                            LoginPageActivity.this,
                            user.getUserId(),
                            user.getEmail()
                    );
                    startActivity(intent);
                    finish(); // Close LoginPageActivity
                } else {
                    // Password does not match
                    toastMaker("Invalid password. Please try again.");
                }
            } else {
                // No user found with this email
                toastMaker("No account found with this email.");
            }
        });
    }

    private void toastMaker(String message) {
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}