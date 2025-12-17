package com.example.coo_bookshelf.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coo_bookshelf.database.DbConfig;
import com.example.coo_bookshelf.database.entities.BookCategory;

import java.util.List;

@Dao
public interface BookCategoryDAO {

    @Insert
    void insert(BookCategory bookCategory);

    @Update
    void update(BookCategory bookCategory);

    @Delete
    void delete(BookCategory bookCategory);

    @Query("SELECT * FROM " + DbConfig.BOOK_CATEGORY_TABLE + " WHERE BookID = :bookId")
    LiveData<List<BookCategory>> getCategoriesForBook(int bookId);

    @Query("DELETE FROM " + DbConfig.BOOK_CATEGORY_TABLE)
    void deleteAll();
}
