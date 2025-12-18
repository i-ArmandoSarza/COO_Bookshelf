package com.example.coo_bookshelf.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coo_bookshelf.database.BookshelfDatabase;
import com.example.coo_bookshelf.database.DbConfig;
import com.example.coo_bookshelf.database.entities.Book;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface BookDAO {

  @Insert
  void insert(Book book);

  @Update
  void update(Book... books);

  @Delete
  void delete(Book... books);

  @Query("SELECT * FROM " + DbConfig.BOOK_TABLE + " WHERE UserId = :userId")
  LiveData<List<Book>> getBooksByUserId(int userId);

  @Query("SELECT COUNT(*) FROM " + DbConfig.BOOK_TABLE + " WHERE UserId = :userId")
  LiveData<Integer> getBookCountByUserId(int userId);

  @Query("SELECT * FROM " + DbConfig.BOOK_TABLE + " WHERE BookId = :bookId")
  LiveData<Book> getBookById(int bookId);

  @Query("DELETE FROM " + DbConfig.BOOK_TABLE)
  void deleteAll();
}
