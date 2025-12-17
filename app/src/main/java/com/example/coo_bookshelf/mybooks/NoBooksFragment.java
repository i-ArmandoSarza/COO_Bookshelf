package com.example.coo_bookshelf.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.booksearch.MyBookSearchActivity;
import com.example.coo_bookshelf.databinding.MyBookNoBooksFoundFragmentBinding;

public class NoBooksFragment extends Fragment {
  private MyBookNoBooksFoundFragmentBinding binding;
  private final int USERID;

  public NoBooksFragment(int userId) {
    super(R.layout.my_book_no_books_found_fragment);
    this.USERID = userId;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = MyBookNoBooksFoundFragmentBinding.inflate(inflater, container, false);

    binding.noBookFoundAddBookButton.setOnClickListener(v -> {
      // TODO: Implement add book functionality
      Intent intent = MyBookSearchActivity.MyBookSearchActivityIntentFactory(requireContext(), USERID);
      startActivity(intent);
    });

    return binding.getRoot();
  }
}
