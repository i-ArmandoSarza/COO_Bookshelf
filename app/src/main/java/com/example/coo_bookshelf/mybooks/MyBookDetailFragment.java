package com.example.coo_bookshelf.mybooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.coo_bookshelf.R;

public class MyBookDetailFragment extends Fragment {

  private static final String BOOK_TITLE = "book_title";
  private static final String BOOK_IMAGE_URL = "book_image_url";
  private static final String BOOK_AUTHOR = "book_author";
  private static final String BOOK_ISBN = "book_isbn";
  private static final String BOOK_PUBLISH_DATE = "book_publish_date";
  private static final String BOOK_DESCRIPTION = "book_description";

  public MyBookDetailFragment() {
    // Required empty constructor
  }

  public static MyBookDetailFragment newInstance(String title, String imageUrl, String author
      , String isbn, String publishDate, String description) {
    MyBookDetailFragment fragment = new MyBookDetailFragment();
    Bundle args = new Bundle();

    args.putString(BOOK_TITLE, title);
    args.putString(BOOK_IMAGE_URL, imageUrl);
    args.putString(BOOK_AUTHOR, author);
    args.putString(BOOK_ISBN, isbn);
    args.putString(BOOK_PUBLISH_DATE, publishDate);
    args.putString(BOOK_DESCRIPTION, description);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.my_book_detail_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    TextView backButton = view.findViewById(R.id.backButton);
    ImageView detailImage = view.findViewById(R.id.detailImage);
    TextView detailTitle = view.findViewById(R.id.detailTitle);
    TextView detailAuthor = view.findViewById(R.id.detailAuthor);
    TextView detailPublishDate = view.findViewById(R.id.bookPublishDate);
    TextView detailDescription = view.findViewById(R.id.bookDescription);
    TextView detailIsbn = view.findViewById(R.id.bookIsbn);

    backButton.setOnClickListener(v -> {
      requireActivity().getSupportFragmentManager().popBackStack();
    });

    if (getArguments() != null) {
      String title = getArguments().getString(BOOK_TITLE);
      String imageUrl = getArguments().getString(BOOK_IMAGE_URL);
      String author = getArguments().getString(BOOK_AUTHOR);
      String description = getArguments().getString(BOOK_DESCRIPTION);
      String isbn = getArguments().getString(BOOK_ISBN);
      String publishDate = getArguments().getString(BOOK_PUBLISH_DATE);

      detailTitle.setText(title);
      detailAuthor.setText(author);
      detailPublishDate.setText(publishDate);
      detailDescription.setText(description);
      detailIsbn.setText(isbn);

      Glide.with(requireContext())
          .load(imageUrl)
          .placeholder(R.drawable.ic_launcher_foreground)
          .error(R.drawable.ic_launcher_foreground)
          .into(detailImage);
    }
  }
}
