package com.example.coo_bookshelf.services;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DocumentsResponse {

  public DocumentsResponse(String authorKey, ArrayList<String> authorName, String firstPublishYear,
      Integer coverId, ArrayList<String> language, String lendingEdition, String lendingIdentifier,
      String worksIdKey, String title) {
    this.authorKey = authorKey;
    this.authorName = authorName;
    this.firstPublishYear = firstPublishYear;
    this.coverId = coverId;
    this.language = language;
    this.lendingEdition = lendingEdition;
    this.lendingIdentifier = lendingIdentifier;
    this.worksIdKey = worksIdKey;
    this.title = title;
  }

  // Example of search response.
  // https://openlibrary.org/search.json?title=Kite runner&lang=en
  @SerializedName("author_key")
  private String authorKey;
  @SerializedName("author_name")
  private ArrayList<String> authorName;
  @SerializedName("first_publish_year")
  private String firstPublishYear;
  @SerializedName("cover_i")
  private Integer coverId;
  @SerializedName("language")
  private ArrayList<String> language;
  @SerializedName("lending_edition_s")
  private String lendingEdition;
  @SerializedName("lending_identifier_s")
  private String lendingIdentifier;
  @SerializedName("key")
  private String worksIdKey;
  @SerializedName("title")
  private String title;

  public String getAuthorKey() {
    return authorKey;
  }

  public ArrayList<String> getAuthorName() {
    return authorName;
  }

  public String getFirstPublishYear() {
    return firstPublishYear;
  }

  public Integer getCoverId() {
    return coverId;
  }

  public ArrayList<String> getLanguage() {
    return language;
  }

  public String getLendingEdition() {
    return lendingEdition;
  }

  public String getLendingIdentifier() {
    return lendingIdentifier;
  }

  public String getWorksIdKey() {
    return worksIdKey;
  }

  public String getTitle() {
    return title;
  }

}
