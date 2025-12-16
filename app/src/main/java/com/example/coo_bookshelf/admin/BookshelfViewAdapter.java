package com.example.coo_bookshelf.admin;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.example.coo_bookshelf.database.entities.User;

/**
 * Name: Rose Arias-Aceves Date: 12/13/25 Explanation: What is this class?
 */
public class BookshelfViewAdapter extends ListAdapter<User, BookshelfViewHolder> {

  public interface OnUserClickListener {

    void onUserClick(User user);
  }

  public OnUserClickListener listener;


  public BookshelfViewAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallBack,
      OnUserClickListener listener) {
    super(diffCallBack);
    this.listener = listener;
  }

  @NonNull
  @Override
  public BookshelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return BookshelfViewHolder.create(parent);
  }

  @Override
  public void onBindViewHolder(@NonNull BookshelfViewHolder holder, int position) {
    User current = getItem(position);
    holder.bind(current, listener);
  }

  public static class BookshelftDiff extends DiffUtil.ItemCallback<User> {

    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
      return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
      return oldItem.equals(newItem);
    }
  }
}
