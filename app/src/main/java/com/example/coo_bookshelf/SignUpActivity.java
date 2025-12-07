package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coo_bookshelf.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    // Helper object connects to views in activity_sign_up.xml
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Build screen from XML layout
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        // Show screen on the device
        setContentView(binding.getRoot());

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

                // TODO: Add method to save new user in database.

                // Make a default user for testing purposes.
                // TODO: Replace this with actual user creation logic.
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                startActivity(intent);
                finish();
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
}