package com.example.coo_bookshelf;

public class MyBookItem {

  private String title;
  private String imageUrl;
  private String author;
  private String isbn;
  private String publishDate;
  private String description;

  public MyBookItem(String title, String imageUrl, String author, String isbn,
      String publishDate, String description) {
    this.title = title;
    this.imageUrl = imageUrl;
    this.author = author;
    this.isbn = isbn;
    this.publishDate = publishDate;
    this.description = description;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getAuthor() {
    return author;
  }
}
