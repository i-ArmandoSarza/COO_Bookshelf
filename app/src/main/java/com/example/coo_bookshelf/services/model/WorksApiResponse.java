package com.example.coo_bookshelf.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorksApiResponse {

  // not the full response.
  // see https://openlibrary.org/works/OL5781992W
  // or if using the browser https://openlibrary.org/works/OL5781992W.json

  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("title")
  @Expose
  private String title;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
