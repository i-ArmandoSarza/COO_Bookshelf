package com.example.coo_bookshelf.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.database.entities.User;
import com.example.coo_bookshelf.databinding.ActivityAdminRemoveUserBinding;
import com.example.coo_bookshelf.admin.BookshelfViewAdapter.BookshelftDiff;

public class AdminRemoveUser extends AppCompatActivity {

  private ActivityAdminRemoveUserBinding binding;
  private static BookshelfRepository repository;
  private BookshelfViewModel bookshelfViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminRemoveUserBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Setup Toolbar: back button
    setSupportActionBar(binding.toolbar);
    if(getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);    // hide title text
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // show back arrow
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    bookshelfViewModel = new ViewModelProvider(this).get(BookshelfViewModel.class);

    //setting up recycler view
    //Mostly adapted from professors recycler view video, and some of
    //Steve's book recycle view.
    RecyclerView recyclerView = binding.AdminUserDisplayRecyclerView;
    final BookshelfViewAdapter adapter = new BookshelfViewAdapter(
        new BookshelftDiff(), user -> {
      showDialogBox(user);
    });

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    repository = BookshelfRepository.getRepository(getApplication());

    bookshelfViewModel.getAllUsers().observe(this, User -> {
      adapter.submitList(User);
    });
  }

  //adapted from https://www.geeksforgeeks.org/android/how-to-display-a-yes-no-dialogbox-in-android/
  private void showDialogBox(User user) {
    DialogInterface.OnClickListener dialogListener = new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
          case DialogInterface.BUTTON_POSITIVE:
            deleteUser(user);
            break;
          case DialogInterface.BUTTON_NEGATIVE:
            dialog.dismiss();
            break;
        }
      }
    };

    AlertDialog.Builder builder = new Builder(AdminRemoveUser.this);
    builder.setMessage("Are you sure you want to delete " + user.getFirstName())
        .setPositiveButton("Yes", dialogListener)
        .setNegativeButton("No", dialogListener).show();
  }

  private void deleteUser(User user) {
    bookshelfViewModel.removeUser(user);
  }

  // Toolbar - Back button
  @Override
  public boolean onSupportNavigateUp() {
    finish();   // go back to previous Activity
    return true;
  }

  static Intent AdminRemoveUserIntentFactory(Context context) {
    Intent intent = new Intent(context, AdminRemoveUser.class);
    return intent;
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}