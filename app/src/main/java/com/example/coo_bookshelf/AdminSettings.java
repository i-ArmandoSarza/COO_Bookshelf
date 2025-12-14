package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityAdminSettingsBinding;
import com.example.coo_bookshelf.databinding.ActivityMyBooksBinding;


public class AdminSettings extends AppCompatActivity {

  private static BookshelfRepository repository;
  private ActivityAdminSettingsBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());
    repository = BookshelfRepository.getRepository(getApplication());

    // When the "Remove User" text is clicked on the login screen
    binding.RemoveUserButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = AdminRemoveUser.AdminRemoveUserIntentFactory(getApplicationContext());
        startActivity(intent);
      }
    });
  }

  // setup intent for AdminSettings
  static Intent AdminSettingsIntentFactory(Context context) {
    Intent intent = new Intent(context, AdminSettings.class);
    return intent;
  }
}