package com.example.coo_bookshelf.bookseach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.mybooks.MyBookItem;
import java.util.List;

public class MyBookSearchAdapter extends RecyclerView.Adapter<MyBookSearchAdapter.ViewHolder> {


  public interface OnItemClickListener {

    void onItemClick(MyBookItem item);
  }

  private List<MyBookItem> items;
  private OnItemClickListener listener;

  public MyBookSearchAdapter(List<MyBookItem> items, OnItemClickListener listener) {
    this.items = items;
    this.listener = listener;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageItem;
    TextView textItem;
    TextView authorName;
    TextView publishDate;
    Button btnAddBook;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      // Initialize the views in the item layout
      // These represent the id's in the view we want to bind the data to
      imageItem = itemView.findViewById(R.id.imageItem);
      textItem = itemView.findViewById(R.id.textItem);
      authorName = itemView.findViewById(R.id.textAuthor);
      publishDate = itemView.findViewById(R.id.textPublished);
      btnAddBook = itemView.findViewById(R.id.btnAddBook);
    }

    // Set up the onClick to save the datas.
    public void bind(MyBookItem item, OnItemClickListener listener) {
      btnAddBook.setOnClickListener(v -> {
        if (listener != null) {
          listener.onItemClick(item);
        }
      });
    }
  }

  @NonNull
  @Override
  public MyBookSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.my_book_search_item_card, parent, false);  // Set the layout we want
    return new MyBookSearchAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyBookSearchAdapter.ViewHolder holder, int position) {
    MyBookItem item = items.get(position);
    // Set the text for the textview's from the MyBookItem
    var author = String.format("Author(s): %s", item.getAuthor());
    var publishDate = String.format("Published: %s", item.getPublishedDate());

    holder.textItem.setText(item.getTitle());
    holder.authorName.setText(author);
    holder.publishDate.setText(publishDate);

    // fetch the image form the url and load it into the imageview
    Glide.with(holder.itemView.getContext())
        .load(item.getImageUrl())
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)
        .into(holder.imageItem);

    holder.bind(item, listener);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }
}
