package com.example.coo_bookshelf.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coo_bookshelf.database.DbConfig;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.database.entities.User;
import java.util.List;


@Dao
public interface UserDAO {

  @Insert
  void insert(User user);
  @Delete
  void delete(User user);
  @Update
  void update(User user);

  // Delete all users from the table.
  @Query("DELETE FROM " + DbConfig.USER_TABLE)
  void deleteAll();

  // Get users by userId
  @Query("SELECT * FROM " + DbConfig.USER_TABLE +
          " WHERE userId = :userId")
  LiveData<User> getUserByUserId(int userId);

  // Synchronous lookup by userId
  @Query("SELECT userId FROM " + DbConfig.USER_TABLE +
          " WHERE userId = :userId")
  int getUserByUserIdSync(int userId);

  @Query("SELECT * FROM " + DbConfig.USER_TABLE)
  LiveData<List<User>> getAllUsersLiveData();

  // Get userId by email (sync)
  @Query("SELECT userId FROM " + DbConfig.USER_TABLE +
          " WHERE email = :email")
  int getUserIdByUserEmailSync(String email);

  // Get users by users email address.
  @Query("SELECT * FROM " + DbConfig.USER_TABLE +
          " WHERE email = :email")
  LiveData<User> getUserByUserEmail(String email);

  // Get single user by email and password (sync, for log in verification)
  @Query("SELECT * FROM " + DbConfig.USER_TABLE +
          " WHERE email = :email AND password = :password LIMIT 1")
  User getUserByEmailAndPasswordSync(String email, String password);

  @Query("UPDATE " + DbConfig.USER_TABLE +
          " SET FirstName = :firstName, LastName = :lastName " +
          " WHERE userId = :userId")
  void updateUserName(int userId, String firstName, String lastName);


}
