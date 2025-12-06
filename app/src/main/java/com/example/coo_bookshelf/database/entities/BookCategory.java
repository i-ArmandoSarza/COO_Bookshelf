package com.example.coo_bookshelf.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;
import com.example.coo_bookshelf.database.BookshelfDatabase;
import java.util.Objects;

@Entity(tableName = BookshelfDatabase.BOOK_CATEGORY_TABLE)
public class BookCategory {

  public BookCategory(int bookID, int categoryID) {
    this.bookID = bookID;
    this.categoryID = categoryID;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookCategory that = (BookCategory) o;
    return bookCategoryId == that.bookCategoryId && bookID == that.bookID
        && categoryID == that.categoryID;
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookCategoryId, bookID, categoryID);
  }

  public int getBookCategoryId() {
    return bookCategoryId;
  }

  public void setBookCategoryId(int bookCategoryId) {
    this.bookCategoryId = bookCategoryId;
  }

  public int getBookID() {
    return bookID;
  }

  public void setBookID(int bookID) {
    this.bookID = bookID;
  }

  public int getCategoryID() {
    return categoryID;
  }

  public void setCategoryID(int categoryID) {
    this.categoryID = categoryID;
  }

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name="BookCategoryId")
  private int bookCategoryId;

  @ColumnInfo(name="BookID")
  private int bookID;
  @ColumnInfo(name="CategoryID")
  private int categoryID;
}
