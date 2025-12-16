package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityAdditionalUserInfoBinding;

public class AdditionalUserInfoActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.coo_bookshelf.USER_ID";
    private static final String USER_EMAIL_KEY = "com.example.coo_bookshelf.USER_EMAIL";

    private ActivityAdditionalUserInfoBinding binding;
    private static BookshelfRepository repository;
    private int userId = -1;
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdditionalUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userId = intent.getIntExtra(USER_ID_KEY, -1);
        userEmail = intent.getStringExtra(USER_EMAIL_KEY);

        repository = BookshelfRepository.getRepository(getApplication());

        // Get USER_ID passed from SignUpActivity
        userId = getIntent().getIntExtra("USER_ID", -1);
        if(userId == -1) {
            toastMaker("Error: no user ID.");
            finish();
            return;
        }

        // Save profile button click
        binding.saveButton.setOnClickListener(v-> saveUserInfo());

    }

    public static Intent additionalInfoIntentFactory(Context context, int userId, String email) {
        Intent intent = new Intent(context, AdditionalUserInfoActivity.class);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("USER_EMAIL", email); // Add email to intent extras
        return intent;
    }

    private void saveUserInfo() {
        String firstName = binding.firstnameInput.getText().toString().trim();
        String lastName = binding.lastNameInput.getText().toString().trim();

        if(firstName.isEmpty() || lastName.isEmpty()) {
            toastMaker("Please enter both first and last name.");
            return;
        }

        // Update DB via repository
        repository.updateUserName(userId, firstName, lastName);

        // Go to MainActivity with the same userId and email
        Intent intent = MainActivity.mainActivityIntentFactory(
                AdditionalUserInfoActivity.this,
                userId,
                userEmail
        );
        startActivity(intent);
        finish();
    }
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}