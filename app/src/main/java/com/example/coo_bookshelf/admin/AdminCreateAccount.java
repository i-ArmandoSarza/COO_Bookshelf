package com.example.coo_bookshelf.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.DAO.UserDAO;

import com.example.coo_bookshelf.database.entities.User;
import com.example.coo_bookshelf.databinding.ActivityAdminCreateAccountBinding;
import com.example.coo_bookshelf.validation.SignupValidator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AdminCreateAccount extends AppCompatActivity {

  private ActivityAdminCreateAccountBinding binding;

  // DATABASE INFO
  private UserDAO userDAO;
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminCreateAccountBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Setup Toolbar: back button
    setSupportActionBar(binding.toolbar);
    if(getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);    // hide title text
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // show back arrow
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    userDAO = BookshelfDatabase.getDatabase(this).userDAO();
    //This section is adapted from the main signup page.
    // Android does not allow database operations on the main thread.
    // We use an Executor to run database operations on a background thread.
    binding.signUpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Read user input from EditText fields
        String email = binding.emailSignUpEditText.getText().toString().trim();
        String password = binding.passwordSignUpEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();
        String firstName = binding.firstNameEditText.getText().toString().trim();
        String lastName = binding.lastNameEditText.getText().toString().trim();
        boolean adminIsChecked = binding.adminCheckBox.isChecked();

        // Check if input is empty
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
          toastMaker("Please fill in all fields.");
          return;
        }

        // Email validation via SignupValidator
        if (!SignupValidator.isEmailValid(email)) {
          toastMaker("Please enter a valid email address.");
          return;
        }

        // Password validation via SignupValidator
        if (!SignupValidator.isPasswordValid(password)) {
          toastMaker("Password must be at least 4 characters.");
          return;
        }

        // Confirm passwords match
        if (!password.equals(confirmPassword)) {
          toastMaker("Passwords do not match.");
          return;
        }

        //if first name is empty or not
        if (firstName.isEmpty() || lastName.isEmpty()) {
          toastMaker("Please enter both first and last name.");
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
            user.setAdmin(adminIsChecked);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userDAO.insert(user);

            // Switch back to main thread to show Toast
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                toastMaker("User " + firstName + " created successfully");
                finish();
              }
            });
          }
        });
      }
    });
  }

  // Toolbar - Back button
  @Override
  public boolean onSupportNavigateUp() {
    finish();   // go back to previous Activity
    return true;
  }


  /** Helper method to open SignUpActivity from other activities */
  static Intent adminCreateAccountIntentFactory(Context context) {
    return new Intent(context, AdminCreateAccount.class);
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

}