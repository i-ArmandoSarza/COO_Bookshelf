package com.example.coo_bookshelf.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.coo_bookshelf.database.BookshelfDatabase;

@Entity(tableName = BookshelfDatabase.BOOK_TABLE)
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int bookID;

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
}
