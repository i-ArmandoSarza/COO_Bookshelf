package com.example.coo_bookshelf.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.coo_bookshelf.database.BookshelfDatabase;

@Entity(tableName = BookshelfDatabase.CATEGORY_TABLE)
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int categoryID;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
