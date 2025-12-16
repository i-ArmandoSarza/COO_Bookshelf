package com.example.coo_bookshelf.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.database.BookshelfRepository;
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

    bookshelfViewModel = new ViewModelProvider(this).get(BookshelfViewModel.class);


    RecyclerView recyclerView = binding.AdminUserDisplayRecyclerView;
    final BookshelfViewAdapter adapter = new BookshelfViewAdapter(new BookshelftDiff(),user -> {toastMaker("Clicked " + user.getFirstName());});

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    repository = BookshelfRepository.getRepository(getApplication());

    bookshelfViewModel.getAllUsers().observe(this, User -> {
      adapter.submitList(User);

    });
  }

  static Intent AdminRemoveUserIntentFactory(Context context) {
    Intent intent = new Intent(context, AdminRemoveUser.class);
    return intent;
  }
  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

}