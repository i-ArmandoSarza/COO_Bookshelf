package com.example.coo_bookshelf.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.coo_bookshelf.database.DbConfig;
import java.util.Objects;

@Entity(tableName = DbConfig.BOOK_TABLE)
public class Book {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "BookId")
  private int bookId;
  @ColumnInfo(name = "UserId")
  private int userId;
  @ColumnInfo(name = "Title")
  private String title;
  @ColumnInfo(name = "Author")
  private String author;
  // OL Author Key, save for later to search by author later?
  @ColumnInfo(name = "AuthorKey")
  private String authorKey;
  @ColumnInfo(name = "FirstPublishedYear")
  private String firstPublishedYear;
  @ColumnInfo(name = "Description")
  private String description;
  @ColumnInfo(name = "ImageUrl")
  private String imageUrl;
  @ColumnInfo(name = "Isbn")
  private String isbn;
  // The OL workId
  // Cover ID is located in https://openlibrary.org/works/OL2089932W
  @ColumnInfo(name = "WorksId")
  private String worksId;

  public Book(int userId, String title, String author, String authorKey, String worksId) {
    // TODO: Probably will need to update the ctor based on how we plan on using books.
    this.userId = userId;
    this.title = title;
    this.author = author;
    this.authorKey = authorKey;
    this.worksId = worksId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getFirstPublishedYear() {
    return firstPublishedYear;
  }

  public void setFirstPublishedYear(String firstPublishedYear) {
    this.firstPublishedYear = firstPublishedYear;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return userId == book.userId && Objects.equals(title, book.title)
        && Objects.equals(author, book.author) && Objects.equals(authorKey,
        book.authorKey) && Objects.equals(firstPublishedYear, book.firstPublishedYear)
        && Objects.equals(description, book.description) && Objects.equals(
        imageUrl, book.imageUrl) && Objects.equals(isbn, book.isbn)
        && Objects.equals(worksId, book.worksId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, title, author, authorKey, firstPublishedYear, description, imageUrl,
        isbn, worksId);
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAuthorKey() {
    return authorKey;
  }

  public void setAuthorKey(String authorKey) {
    this.authorKey = authorKey;
  }

  public String getWorksId() {
    return worksId;
  }

  public void setWorksId(String workId) {
    this.worksId = workId;
  }

}
