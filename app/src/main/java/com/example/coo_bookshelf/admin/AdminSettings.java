package com.example.coo_bookshelf.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityAdminSettingsBinding;


public class AdminSettings extends AppCompatActivity {

  private static BookshelfRepository repository;
  private ActivityAdminSettingsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());
    repository = BookshelfRepository.getRepository(getApplication());

    // Setup Toolbar and enable Back Button
    setSupportActionBar(binding.toolbar);
    if(getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // When the "Add User" text is clicked on the admin screen
    binding.AddUserButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = AdminCreateAccount.adminCreateAccountIntentFactory(getApplicationContext());
        startActivity(intent);
      }
    });

    // When the "Remove User" text is clicked on the admin screen
    binding.RemoveUserButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = AdminRemoveUser.AdminRemoveUserIntentFactory(getApplicationContext());
        startActivity(intent);
      }
    });
  }

  // Back Button
  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  // setup intent for AdminSettings
  public static Intent AdminSettingsIntentFactory(Context context) {
    Intent intent = new Intent(context, AdminSettings.class);
    return intent;
  }
}