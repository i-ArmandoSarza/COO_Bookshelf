package com.example.coo_bookshelf.database.entities;

import androidx.room.ColumnInfo;
import com.example.coo_bookshelf.database.BookshelfDatabase;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.coo_bookshelf.database.DbConfig;
import java.util.Objects;

@Entity(tableName = DbConfig.USER_TABLE)
public class User {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "UserID")
  private int userId;
  @ColumnInfo(name = "FirstName")
  private String firstName;
  @ColumnInfo(name = "Email")
  private String email;
  @ColumnInfo(name = "IsAdmin")
  private boolean isAdmin;
  @ColumnInfo(name = "Password")
  private String password;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
    isAdmin = false;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User users = (User) o;
    return userId == users.userId && isAdmin == users.isAdmin && Objects.equals(firstName,
        users.firstName) && Objects.equals(password, users.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstName, isAdmin, password);
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
