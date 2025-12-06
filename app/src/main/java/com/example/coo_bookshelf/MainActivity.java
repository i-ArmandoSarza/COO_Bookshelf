package com.example.coo_bookshelf;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
  private static BookshelfRepository repository;
  private ActivityMainBinding binding;
  public static final String TAG = "CCO_Bookshelf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding = ActivityMainBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
        repository = BookshelfRepository.getRepository(getApplication());

    }
}