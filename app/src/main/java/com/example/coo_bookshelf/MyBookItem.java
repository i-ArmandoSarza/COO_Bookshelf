package com.example.coo_bookshelf;

public class MyBookItem {
  private String title;
  private String imageUrl;
  private String author;

  public MyBookItem(String title, String imageUrl, String description) {
    this.title = title;
    this.imageUrl = imageUrl;
    this.author = description;
  }

  public String getTitle() {
    return title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getAuthor() {
    return author;
  }
}
