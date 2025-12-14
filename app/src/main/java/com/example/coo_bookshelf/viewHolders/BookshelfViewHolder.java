package com.example.coo_bookshelf.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;

/**
* Name: Rose Arias-Aceves
* Date: 12/13/25
* Explanation: What is this class?
*/
public class BookshelfViewHolder extends RecyclerView.ViewHolder {

  private final TextView bookshelfViewItem;

  private BookshelfViewHolder(View bookshelfView){
    super(bookshelfView);
    bookshelfViewItem = bookshelfView.findViewById(R.id.recyclerAdminItemTextview);
  }

  public void bind(String text){
      bookshelfViewItem.setText(text);
  }
  static BookshelfViewHolder create(ViewGroup parent){
    View view = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.admin_user_recycler_item,parent,false);
        return new BookshelfViewHolder(view);
  }
}
