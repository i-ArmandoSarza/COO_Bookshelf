package com.example.coo_bookshelf.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.coo_bookshelf.database.BookshelfDatabase;

@Entity(tableName = BookshelfDatabase.CATEGORY_TABLE)
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int categoryID;
}
