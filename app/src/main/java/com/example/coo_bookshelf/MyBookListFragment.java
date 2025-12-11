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
    // mock data.
    List<MyBookItem> data = Arrays.asList(
        new MyBookItem(
            "Red Rising",
            "https://covers.openlibrary.org/b/isbn/9780593871522-L.jpg",
            "Pierce Brown.",
            "9780593871522",
            "2012",
            "Book Description"

        ),
        new MyBookItem(
            "The Kite Runner",
            "https://covers.openlibrary.org/b/isbn/1417640391-L.jpg",
            "Khaled Hosseini",
            "9867475658",
            "2013",
            "The unforgettable, heartbreaking story of the unlikely friendship between a "
                + "wealthy boy and the son of his father’s servant, The Kite Runner is a beautifully "
                + "crafted novel set in a country that is in the process of being destroyed. It is "
                + "about the power of reading, the price of betrayal, and the possibility of "
                + "redemption; and an exploration of the power of fathers over sons—their love, "
                + "their sacrifices, their lies.A sweeping story of family, love, "
                + "and friendship told against the devastating backdrop of the history of Afghanistan"
                + " over the last thirty years, The Kite Runner is an unusual and powerful novel that has become a beloved, one-of-a-kind classic."

        ),
        new MyBookItem(
            "Artemis",
            "https://covers.openlibrary.org/b/id/12639918-L.jpg",
            "Andy Weir",
            "9867475658",
            "2017",
            "Book Description"
        )
    );


    MyBookAdapter adapter = new MyBookAdapter(data, myBookItem -> {
      // Navigate to detail fragment on click
      MyBookDetailFragment detailFragment = MyBookDetailFragment.newInstance(
          myBookItem.getTitle(),
          myBookItem.getImageUrl(),
          myBookItem.getAuthor(),
          myBookItem.getIsbn(),
          myBookItem.getPublishDate(),
          myBookItem.getDescription()
      );

      // Replace the current fragment with the detail fragment
      // Display the details from the selected book tile.
      requireActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.myBookFragmentContainerView, detailFragment)
          .addToBackStack(null)
          .commit();
    });

    recyclerView.setAdapter(adapter);
  }
}
