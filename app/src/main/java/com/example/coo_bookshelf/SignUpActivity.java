package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.DAO.UserDAO;
import com.example.coo_bookshelf.database.entities.User;
import com.example.coo_bookshelf.databinding.ActivitySignUpBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    // Helper object connects to views in activity_sign_up.xml
    private ActivitySignUpBinding binding;

    // DATABASE INFO
    private UserDAO userDAO;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Build screen from XML layout
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        // Show screen on the device
        setContentView(binding.getRoot());

        // Setup Toolbar: back button
        setSupportActionBar(binding.toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);    // hide title text
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // show back arrow
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userDAO = BookshelfDatabase.getDatabase(this).userDAO();
        // Android does not allow database operations on the main thread.
        // We use an Executor to run database operations on a background thread.
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read user input from EditText fields
                String email = binding.emailSignUpEditText.getText().toString().trim();
                String password = binding.passwordSignUpEditText.getText().toString().trim();
                String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

                // Check if input is empty
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(
                            SignUpActivity.this,
                            "Please fill in all fields.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(
                            SignUpActivity.this,
                            "Passwords do not match.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Save user in database on a background thread
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Create the user and insert into database (this runs off the main thread)
                        User user = new User(email, password);
                        userDAO.insert(user);

                        // Get new user's ID by email (implemented in UserDAO)
                        int newUserId = userDAO.getUserIdByUserEmailSync(email);

                        // Switch back to main thread to show Toast and navigate
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Go to MainActivity with this user id
                                Intent intent = AdditionalUserInfoActivity.additionalInfoIntentFactory(
                                        SignUpActivity.this, newUserId
                                );
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
            }
        });

        // When the "Log In" text is clicked on the sign up screen
        binding.loginPageLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a plan to go to the LoginPageActivity
                Intent intent = LoginPageActivity.loginIntentFactory(SignUpActivity.this);
                startActivity(intent);
            }
        });
    }

    /** Helper method to open SignUpActivity from other activities */
    static Intent signUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    // ToolBar back arrow click
    @Override
    public boolean onSupportNavigateUp() {
        finish();   // go back to previous Activity
        return true;
    }
}
