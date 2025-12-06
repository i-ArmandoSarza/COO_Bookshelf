package com.example.coo_bookshelf.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.coo_bookshelf.database.BookshelfDatabase;
import java.util.Objects;

@Entity(tableName = BookshelfDatabase.CATEGORY_TABLE)
public class Category {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "CategoryID")
  private int categoryId;
  @ColumnInfo(name = "CategoryName")
  private String categoryName;

  public Category(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return categoryId == category.categoryId && Objects.equals(categoryName,
        category.categoryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryId, categoryName);
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }
}
