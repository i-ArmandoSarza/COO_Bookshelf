package com.example.coo_bookshelf.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.entities.User;

/**
 * Name: Rose Arias-Aceves Date: 12/13/25 Explanation: What is this class?
 */
public class BookshelfViewHolder extends RecyclerView.ViewHolder {

  TextView textItem;

  private BookshelfViewHolder(View itemView) {
    super(itemView);
    textItem = itemView.findViewById(R.id.textItem);

  }

  public void bind(User user, BookshelfViewAdapter.OnUserClickListener listener) {
    textItem.setText(user.getFirstName());
    itemView.setOnClickListener(v -> {
      if (listener != null) {
        listener.onUserClick(user);
      }
    });
  }

  static BookshelfViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.my_book_item_card, parent, false);
    return new BookshelfViewHolder(view);
  }


}
