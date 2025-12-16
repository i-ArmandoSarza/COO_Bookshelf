package com.example.coo_bookshelf.services;

import com.example.coo_bookshelf.mybooks.MyBookItem;
import com.example.coo_bookshelf.services.model.SearchApiResponse;
import java.util.ArrayList;
import java.util.Locale;

public class BookMapper {

  private static final String COVER_IMG_URL = "https://covers.openlibrary.org/b/id";

  // This function takes the raw API response and cleans it.
  public static ArrayList<MyBookItem> mapSearchResponseToMyBookItems(SearchApiResponse response) {
    ArrayList<MyBookItem> bookItems = new ArrayList<>();
    if (response == null || response.getDocs() == null) {
      return bookItems; // Return an empty list if there's no data
    }

    for (var doc : response.getDocs()) {
      // Data Cleansing Rule 1: Skip records with no author.
      if (doc.getAuthorKey() == null || doc.getAuthorKey().isEmpty()) {
        continue;
      }

      // Handle missing cover images gracefully.
      String coverUrl = "";
      if (doc.getCoverI() != null && doc.getCoverI() > 0) {
        coverUrl = String.format(Locale.ENGLISH, "%s/%d-M.jpg", COVER_IMG_URL, doc.getCoverI());
      }

      //  Provide default values for missing fields.
      String publishYear =
          (doc.getFirstPublishYear() != null) ? doc.getFirstPublishYear().toString() : "N/A";
      String authorName =
          (doc.getAuthorNameFormated() != null) ? doc.getAuthorNameFormated() : "Unknown Author";

      var book = new MyBookItem(
          doc.getTitle(),
          coverUrl,
          authorName,
          "", // ISBN - blank during initial query, found in -> works/{worksId}/editions
          publishYear,
          ""  // Description - blank during initial query, found in -> works/{worksId}/
      );

      String workId = getWorksId(doc.getKey());
      book.setWorksId(workId);
      book.setAuthor(doc.getAuthorNameFormated());
      book.setAuthorKey(doc.getAuthorKey().getFirst()); // We should display all Authors.
      bookItems.add(book);
    }
    return bookItems;
  }

  // Remove the "/works/" from the workId.
  private static String getWorksId(String rawWorkId) {
    if (rawWorkId == null || rawWorkId.trim().isEmpty()) {
      return "";
    }

    if (rawWorkId.contains("/works/")) {
      return rawWorkId.replace("/works/", "");
    }

    return rawWorkId;
  }

}
