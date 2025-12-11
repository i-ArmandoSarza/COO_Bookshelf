package com.example.coo_bookshelf;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class MyBookListFragment extends Fragment {

  public MyBookListFragment() {
    super(R.layout.my_book_list_fragment);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    // TODO: Get data from DB. This is a test for the proof of concept.
    List<MyBookItem> data = Arrays.asList(
        new MyBookItem(
            "Red Rising",
            "https://covers.openlibrary.org/b/isbn/9780593871522-L.jpg",
            "Pierce Brown."
        ),
        new MyBookItem(
            "The Kite Runner",
            "https://covers.openlibrary.org/b/isbn/9867475658-L.jpg",
            "Khaled Hosseini"
        ),
        new MyBookItem(
            "Artemis",
            "https://covers.openlibrary.org/b/id/12639918-L.jpg",
            "Andy Weir"
        )
    );


    MyBookAdapter adapter = new MyBookAdapter(data, myBookItem -> {
      // Navigate to detail fragment on click
      MyBookDetailFragment detailFragment = MyBookDetailFragment.newInstance(
          myBookItem.getTitle(),
          myBookItem.getImageUrl(),
          myBookItem.getAuthor()
      );
      requireActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.myBookFragmentContainerView, detailFragment)
          .addToBackStack(null)
          .commit();
    });

    recyclerView.setAdapter(adapter);
  }
}
