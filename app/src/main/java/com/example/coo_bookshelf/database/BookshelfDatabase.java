package com.example.coo_bookshelf.database;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.BookCategory;
import com.example.coo_bookshelf.database.entities.Category;
import com.example.coo_bookshelf.database.entities.User;


@Database(entities = {User.class, Book.class, Category.class, BookCategory.class}, version = 1, exportSchema = false)
public abstract class BookshelfDatabase extends RoomDatabase{
    public static final String USER_TABLE = "USER";
    public static final String BOOK_TABLE = "BOOK";
    public static final String CATEGORY_TABLE = "CATEGORIES";
    public static final String BOOK_CATEGORY_TABLE = "BOOK_CATEGORIES";
    private final static String DATABASE_NAME = "BookshelfDatabase";
}
