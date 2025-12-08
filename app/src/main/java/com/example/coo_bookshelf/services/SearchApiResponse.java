package com.example.coo_bookshelf.services;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SearchApiResponse {

  private int numFound;
  @SerializedName("docs")
  private ArrayList<DocumentsResponse> docs;

  public SearchApiResponse(int numFound, ArrayList<DocumentsResponse> docs) {
    this.numFound = numFound;
    this.docs = docs;
  }

  public ArrayList<DocumentsResponse> getDocs() {
    return docs;
  }

  public int getNumFound() {
    return numFound;
  }

}

