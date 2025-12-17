package com.example.coo_bookshelf.mybooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityMyBooksBinding;


public class MyBookActivity  extends AppCompatActivity {
  private static BookshelfRepository repository;
  private ActivityMyBooksBinding binding;
  private int userId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMyBooksBinding.inflate(getLayoutInflater());
//    setContentView(R.layout.activity_main);
    setContentView(binding.getRoot());
    repository = BookshelfRepository.getRepository(getApplication());
    userId = getIntent().getIntExtra("USER_ID", -1);

    if(userId == -1) {
      toastMaker("Error: no user ID.");
      finish();
      return;
    }

//    MyBookListFragment myBookListFragment = new MyBookListFragment(userId);

    if (savedInstanceState == null) {
      repository.getBooksByUserId(userId).observe(this, books -> {
        Fragment fragment;
        if (books == null || books.isEmpty()) {
          fragment = new NoBooksFragment(userId);
        } else {
          fragment = new MyBookListFragment(userId);
        }
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.myBookFragmentContainerView, fragment)
            .commit();
      });
    }
  }

  // setup intent for MyBookActivity
  public static Intent MyBookActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MyBookActivity.class);
    intent.putExtra("USER_ID", userId);
    return intent;
  }

  // MyBook Activity onClick.

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
