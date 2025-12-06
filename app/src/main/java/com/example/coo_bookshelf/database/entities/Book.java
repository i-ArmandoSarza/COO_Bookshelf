package com.example.coo_bookshelf.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.coo_bookshelf.database.BookshelfDatabase;
import java.util.Objects;

@Entity(tableName = BookshelfDatabase.BOOK_TABLE)
public class Book {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "BookId")
  private int bookID;
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
  // The OL workId
  // Cover ID is located in https://openlibrary.org/works/OL2089932W
  @ColumnInfo(name = "WorkId")
  private String workId;

  public Book(int userId, String title, String author, String authorKey, String workId) {
    // TODO: Probably will need to update the ctor based on how we plan on using books.
    this.userId = userId;
    this.title = title;
    this.author = author;
    this.authorKey = authorKey;
    this.workId = workId;
  }

  public String getFirstPublishedYear() {
    return firstPublishedYear;
  }

  public void setFirstPublishedYear(String firstPublishedYear) {
    this.firstPublishedYear = firstPublishedYear;
  }

  // TODO: Check if the equals is using the correct fields we want to match on.
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return bookID == book.bookID && userId == book.userId && Objects.equals(title,
        book.title) && Objects.equals(author, book.author) && Objects.equals(
        authorKey, book.authorKey) && Objects.equals(firstPublishedYear,
        book.firstPublishedYear) && Objects.equals(workId, book.workId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookID, userId, title, author, authorKey, firstPublishedYear, workId);
  }

  public int getBookID() {
    return bookID;
  }

  public void setBookID(int bookID) {
    this.bookID = bookID;
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

  public String getWorkId() {
    return workId;
  }

  public void setWorkId(String workId) {
    this.workId = workId;
  }

}
