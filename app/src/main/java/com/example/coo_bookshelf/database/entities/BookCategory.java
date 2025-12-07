package com.example.coo_bookshelf.database.entities;

import androidx.room.Entity;

import com.example.coo_bookshelf.database.BookshelfDatabase;

@Entity(tableName = BookshelfDatabase.BOOK_CATEGORY_TABLE,
primaryKeys = {"bookID", "categoryID"})
public class BookCategory {
    private int bookID;
    private int categoryID;

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
}
