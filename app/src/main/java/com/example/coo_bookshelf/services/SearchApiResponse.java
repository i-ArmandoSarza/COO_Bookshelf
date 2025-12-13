package com.example.coo_bookshelf.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

// Use https://www.jsonschema2pojo.org/ help create JSON response POJO.
public class SearchApiResponse {

  @SerializedName("start")
  @Expose
  private Integer start;
  @SerializedName("numFoundExact")
  @Expose
  private Boolean numFoundExact;
  @SerializedName("numFound")
  @Expose
  private int numFound;
  @SerializedName("num_found")
  @Expose
  private Integer num_found;
  @SerializedName("documentation_url")
  @Expose
  private String documentationUrl;
  @SerializedName("q")
  @Expose
  private String q;
  ;
  @SerializedName("offset")
  @Expose
  private Object offset;
  @SerializedName("docs")
  @Expose
  private List<DocumentsResponse> docs;

  public Integer getNum_found() {
    return num_found;
  }

  public void setNum_found(Integer num_found) {
    this.num_found = num_found;
  }

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Boolean getNumFoundExact() {
    return numFoundExact;
  }

  public void setNumFoundExact(Boolean numFoundExact) {
    this.numFoundExact = numFoundExact;
  }

  public Integer getNumFound() {
    return numFound;
  }

  public void setNumFound(int numFound) {
    this.numFound = numFound;
  }


  public String getDocumentationUrl() {
    return documentationUrl;
  }

  public void setDocumentationUrl(String documentationUrl) {
    this.documentationUrl = documentationUrl;
  }

  public String getQ() {
    return q;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public Object getOffset() {
    return offset;
  }

  public void setOffset(Object offset) {
    this.offset = offset;
  }

  public List<DocumentsResponse> getDocs() {
    return docs;
  }

  public void setDocs(List<DocumentsResponse> docs) {
    this.docs = docs;
  }

}

