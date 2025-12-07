package com.example.coo_bookshelf.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.coo_bookshelf.database.entities.Book;
import java.util.List;

@Dao
public interface BookDAO {

  @Insert
  void insert(Book books);

  @Update
  void update(Book... books);

  @Delete
  void delete(Book... books);

  @Query("SELECT * FROM Book where userId = :userId")
  LiveData<Book> getBooksByUserId(int userId);

  @Query("SELECT * FROM Book WHERE BookId = :bookId")
  LiveData<Book> getBookById(int bookId);

  @Query("delete from book")
  void deleteAll();

}
