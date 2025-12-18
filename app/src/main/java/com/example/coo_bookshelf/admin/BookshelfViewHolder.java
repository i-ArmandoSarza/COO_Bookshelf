package com.example.coo_bookshelf.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.entities.User;


public class BookshelfViewHolder extends RecyclerView.ViewHolder {

  //This class is adapted from Steve's RecyclerView
  TextView textItem;

  private BookshelfViewHolder(View itemView) {
    super(itemView);
    textItem = itemView.findViewById(R.id.textItem);
  }

  public void bind(User user, BookshelfViewAdapter.OnUserClickListener listener) {
    textItem.setText(user.getFirstName());
    //The 'card' itself is a itemview, I dont actually put any picture there.
    itemView.setOnClickListener(v -> {
      if (listener != null) {
        listener.onUserClick(user);
      }
    });
  }

  static BookshelfViewHolder create(ViewGroup parent) {
    //It inflates the same Activity used in the search book section.
    View view = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.my_book_item_card, parent, false);
    return new BookshelfViewHolder(view);
  }
}
