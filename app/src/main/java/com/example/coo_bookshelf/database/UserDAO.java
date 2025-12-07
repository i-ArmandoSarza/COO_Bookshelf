package com.example.coo_bookshelf.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.coo_bookshelf.database.entities.User;


@Dao
public interface UserDAO {

  @Insert
  void insert(User user);

  @Delete
  void delete(User user);

  @Update
  void update(User user);

  // Delete all users from the table.
  @Query("delete from user")
  void deleteAll();

  // Get users by userId
  @Query("SELECT * FROM user WHERE userId == :userId ")
  LiveData<User> getUserByUserId(int userId);

  // Get users by users email address.
  @Query("SELECT * FROM User WHERE email = :email ")
  LiveData<User> getUserByUserEmail(String email);

}
