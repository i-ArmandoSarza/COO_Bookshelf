package com.example.coo_bookshelf.bookseach;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.mybooks.MyBookAdapter;
import com.example.coo_bookshelf.mybooks.MyBookDetailFragment;
import com.example.coo_bookshelf.mybooks.MyBookItem;
import java.util.ArrayList;

public class MyBookSearchListFragment extends Fragment  {

  private BookshelfRepository repository;
  private int USERID;
  private ArrayList<MyBookItem> myBookItems;
  private MyBookAdapter adapter;


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

   // repository = BookshelfRepository.getRepository(requireActivity().getApplication());

    // Start with an empty list
    //ArrayList<MyBookItem> myBookItems = new ArrayList<>();

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
    adapter.notifyDataSetChanged();
    // Observe LiveData
//    repository.getBooksByUserId(USERID).observe(getViewLifecycleOwner(), books -> {
//
//      myBookItems.clear(); // Clear old data
//
//      if (books != null) {
//        for (Book b : books) {
//          myBookItems.add(new MyBookItem(
//              b.getTitle(),
//              b.getImageUrl(),
//              b.getAuthor(),
//              b.getIsbn(),
//              b.getFirstPublishedYear(),
//              b.getDescription()
//          ));
//        }
//      }
//
//      // Notify the adapter that data changed
//      adapter.notifyDataSetChanged();
//    });
  }
}