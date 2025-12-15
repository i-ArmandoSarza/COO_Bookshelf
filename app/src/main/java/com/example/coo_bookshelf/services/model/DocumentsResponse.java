package com.example.coo_bookshelf.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DocumentsResponse {
  @SerializedName("author_key")
  @Expose
  private List<String> authorKey;
  @SerializedName("author_name")
  @Expose
  private List<String> authorName;
  @SerializedName("cover_edition_key")
  @Expose
  private String coverEditionKey;
  @SerializedName("cover_i")
  @Expose
  private Integer coverI;
  @SerializedName("ebook_access")
  @Expose
  private String ebookAccess;
  @SerializedName("edition_count")
  @Expose
  private Integer editionCount;
  @SerializedName("first_publish_year")
  @Expose
  private Integer firstPublishYear;
  @SerializedName("has_fulltext")
  @Expose
  private Boolean hasFulltext;
  @SerializedName("ia")
  @Expose
  private List<String> ia;
  @SerializedName("ia_collection_s")
  @Expose
  private String iaCollectionS;
  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("language")
  @Expose
  private List<String> language;
  @SerializedName("lending_edition_s")
  @Expose
  private String lendingEditionS;
  @SerializedName("lending_identifier_s")
  @Expose
  private String lendingIdentifierS;
  @SerializedName("public_scan_b")
  @Expose
  private Boolean publicScanB;
  @SerializedName("title")
  @Expose
  private String title;

  public List<String> getAuthorKey() {
    return authorKey;
  }

  public void setAuthorKey(List<String> authorKey) {
    this.authorKey = authorKey;
  }

  public List<String> getAuthorName() {
    return authorName;
  }

  // Returns the string in a format that can be displayed to the user.
  public String getAuthorNameFormated() {
    if(authorName.isEmpty()){
      return "";
    }

    if(authorName.size() == 1){
      return authorName.get(0);
    }

    StringBuilder sb = new StringBuilder();
    for (String name : authorName) {
      sb.append(name).append(", ");
    }

    return sb.toString();
  }

  public void setAuthorName(List<String> authorName) {
    this.authorName = authorName;
  }

  public String getCoverEditionKey() {
    return coverEditionKey;
  }

  public void setCoverEditionKey(String coverEditionKey) {
    this.coverEditionKey = coverEditionKey;
  }

  public Integer getCoverI() {
    return coverI;
  }

  public void setCoverI(Integer coverI) {
    this.coverI = coverI;
  }

  public String getEbookAccess() {
    return ebookAccess;
  }

  public void setEbookAccess(String ebookAccess) {
    this.ebookAccess = ebookAccess;
  }

  public Integer getEditionCount() {
    return editionCount;
  }

  public void setEditionCount(Integer editionCount) {
    this.editionCount = editionCount;
  }

  public Integer getFirstPublishYear() {
    return firstPublishYear;
  }

  public void setFirstPublishYear(Integer firstPublishYear) {
    this.firstPublishYear = firstPublishYear;
  }

  public Boolean getHasFulltext() {
    return hasFulltext;
  }

  public void setHasFulltext(Boolean hasFulltext) {
    this.hasFulltext = hasFulltext;
  }

  public List<String> getIa() {
    return ia;
  }

  public void setIa(List<String> ia) {
    this.ia = ia;
  }

  public String getIaCollectionS() {
    return iaCollectionS;
  }

  public void setIaCollectionS(String iaCollectionS) {
    this.iaCollectionS = iaCollectionS;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public List<String> getLanguage() {
    return language;
  }

  public void setLanguage(List<String> language) {
    this.language = language;
  }

  public String getLendingEditionS() {
    return lendingEditionS;
  }

  public void setLendingEditionS(String lendingEditionS) {
    this.lendingEditionS = lendingEditionS;
  }

  public String getLendingIdentifierS() {
    return lendingIdentifierS;
  }

  public void setLendingIdentifierS(String lendingIdentifierS) {
    this.lendingIdentifierS = lendingIdentifierS;
  }

  public Boolean getPublicScanB() {
    return publicScanB;
  }

  public void setPublicScanB(Boolean publicScanB) {
    this.publicScanB = publicScanB;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
