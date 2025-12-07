package com.example.coo_bookshelf.database.entities;

import com.example.coo_bookshelf.database.BookshelfDatabase;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = BookshelfDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
