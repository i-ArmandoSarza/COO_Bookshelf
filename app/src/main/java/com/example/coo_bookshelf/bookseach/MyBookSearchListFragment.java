package com.example.coo_bookshelf.bookseach;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.mybooks.MyBookAdapter;
import com.example.coo_bookshelf.mybooks.MyBookDetailFragment;
import com.example.coo_bookshelf.mybooks.MyBookItem;
import java.util.ArrayList;

public class MyBookSearchListFragment extends Fragment {

  private BookshelfRepository repository;
  private int USERID;
  private ArrayList<MyBookItem> myBookItems;
  private MyBookSearchAdapter adapter;


  public MyBookSearchListFragment(int userId, ArrayList<MyBookItem> myBookItems) {
    super(R.layout.my_book_list_fragment);
    this.USERID = userId;
    this.myBookItems = myBookItems;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    repository = BookshelfRepository.getRepository(requireActivity().getApplication());

    // TODO: Get the Description from the /works api call.

    // Setup up the data and the items that are selected
    adapter = new MyBookSearchAdapter(myBookItems, selectedItem -> {
      Book bookToSave = new Book(
          this.USERID,
          selectedItem.getTitle(),
          selectedItem.getAuthor(),
          selectedItem.getAuthorKey(),
          selectedItem.getWorksId()
      );
      bookToSave.setImageUrl(selectedItem.getImageUrl());
      bookToSave.setIsbn(selectedItem.getIsbn());
      bookToSave.setFirstPublishedYear(selectedItem.getPublishDate());

      // form api  selectedItem.getDescription()
      repository.insert(bookToSave);
      Toast.makeText(requireContext(), "Book added to your shelf", Toast.LENGTH_SHORT).show();

    });

    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }
}