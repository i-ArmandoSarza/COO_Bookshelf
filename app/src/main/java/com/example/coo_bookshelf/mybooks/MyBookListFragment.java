package com.example.coo_bookshelf.mybooks;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.database.entities.Book;
import java.util.ArrayList;


public class MyBookListFragment extends Fragment {

  private BookshelfRepository repository;
  private int USERID;
  private MyBookAdapter adapter;

  public MyBookListFragment(int userId) {
    super(R.layout.my_book_list_fragment);
    this.USERID = userId;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    repository = BookshelfRepository.getRepository(requireActivity().getApplication());

    // Start with an empty list
    ArrayList<MyBookItem> myBookItems = new ArrayList<>();

    // Create adapter with empty list
    adapter = new MyBookAdapter(myBookItems, selectedItem -> {
      MyBookDetailFragment detailFragment = MyBookDetailFragment.newInstance(
          selectedItem.getTitle(),
          selectedItem.getImageUrl(),
          selectedItem.getAuthor(),
          selectedItem.getIsbn(),
          selectedItem.getPublishDate(),
          selectedItem.getDescription()
      );

      requireActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.myBookFragmentContainerView, detailFragment)
          .addToBackStack(null)
          .commit();
    });

    recyclerView.setAdapter(adapter);

    // Observe LiveData
    repository.getBooksByUserId(USERID).observe(getViewLifecycleOwner(), books -> {

      myBookItems.clear(); // Clear old data

      if (books != null) {
        for (Book b : books) {
          myBookItems.add(new MyBookItem(
              b.getTitle(),
              b.getImageUrl(),
              b.getAuthor(),
              b.getIsbn(),
              b.getFirstPublishedYear(),
              b.getDescription()
          ));
        }
      }

      // Notify the adapter that data changed
      adapter.notifyDataSetChanged();
    });
  }
}