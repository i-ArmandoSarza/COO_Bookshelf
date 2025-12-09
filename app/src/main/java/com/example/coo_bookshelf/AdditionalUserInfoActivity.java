package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityAdditionalUserInfoBinding;

public class AdditionalUserInfoActivity extends AppCompatActivity {

    private ActivityAdditionalUserInfoBinding binding;
    private static BookshelfRepository repository;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdditionalUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    public static Intent additionalInfoIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdditionalUserInfoActivity.class);
        intent.putExtra("USER_ID", userId);
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

        // Go to MainActivity with the same userId
        Intent intent = MainActivity.mainActivityIntentFactory(this, userId);
        startActivity(intent);
        finish();
    }
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}