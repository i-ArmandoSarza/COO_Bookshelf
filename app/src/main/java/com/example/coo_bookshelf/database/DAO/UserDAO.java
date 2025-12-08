package com.example.coo_bookshelf.database.DAO;

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

  @Query("SELECT userId FROM user WHERE userId == :userId ")
  int getUserByUserIdSync(int userId);

  // Get userIs by the user's email address.
  @Query("SELECT UserID FROM User WHERE email = :email ")
  int getUserByUserEmailSync(String email);

  // Get users by users email address.
  @Query("SELECT * FROM User WHERE email = :email ")
  LiveData<User> getUserByUserEmail(String email);

}
