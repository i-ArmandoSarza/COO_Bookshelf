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
import com.example.coo_bookshelf.validation.SignupValidator;

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
                    toastMaker("Please fill in all fields.");
                    return;
                }

                // Email validation via SignupValidator
                if(!SignupValidator.isEmailValid(email)) {
                    toastMaker("Please enter a valid email address.");
                    return;
                }

                // Password validation via SignupValidator
                if(!SignupValidator.isPasswordValid(password)){
                    toastMaker("Password must be at least 4 characters.");
                    return;
                }

                // Confirm passwords match
                if(!password.equals(confirmPassword)) {
                    toastMaker("Passwords do not match.");
                    return;
                }

                // Save user in database on a background thread
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Check if a user with this email already exists
                        int existingUserId = userDAO.getUserIdByUserEmailSync(email);

                        if (existingUserId > 0) {
                            // Email already in use -> show Toast on main thread and stop
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toastMaker("An account with this email already exists.");
                                }
                            });
                            return; // Don't insert a duplicate user
                        }

                        // No existing user found -> proceed to insert new user
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

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
