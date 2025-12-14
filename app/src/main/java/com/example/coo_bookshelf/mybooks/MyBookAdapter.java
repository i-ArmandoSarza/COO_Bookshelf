package com.example.coo_bookshelf.mybooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.coo_bookshelf.R;
import java.util.List;

public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.ViewHolder> {
public interface OnItemClickListener {
  void onItemClick(MyBookItem item);
}

private List<MyBookItem> items;
private OnItemClickListener listener;

public MyBookAdapter(List<MyBookItem> items, OnItemClickListener listener) {
  this.items = items;
  this.listener = listener;
}

public static class ViewHolder extends RecyclerView.ViewHolder {
  ImageView imageItem;
  TextView textItem;

  public ViewHolder(@NonNull View itemView) {
    super(itemView);
    imageItem = itemView.findViewById(R.id.imageItem);
    textItem = itemView.findViewById(R.id.textItem);
  }

  public void bind(MyBookItem item, OnItemClickListener listener) {
    itemView.setOnClickListener(v -> {
      if (listener != null) {
        listener.onItemClick(item);
      }
    });
  }
}

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.my_book_item_card, parent, false);
  return new ViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
  MyBookItem item = items.get(position);

  holder.textItem.setText(item.getTitle());

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

