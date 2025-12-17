package com.example.coo_bookshelf.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coo_bookshelf.database.DbConfig;
import com.example.coo_bookshelf.database.entities.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM " + DbConfig.CATEGORY_TABLE + " ORDER BY CategoryName ASC")
    LiveData<List<Category>> getAllCategories();

    @Query("DELETE FROM " + DbConfig.CATEGORY_TABLE)
    void deleteAll();
}
