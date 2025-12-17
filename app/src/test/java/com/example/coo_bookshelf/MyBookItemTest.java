package com.example.coo_bookshelf;

import com.example.coo_bookshelf.mybooks.MyBookItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyBookItemTest {
  private MyBookItem bookItem;
  private static final String TEST_TITLE = "The Great Gatsby";
  private static final String TEST_IMAGE_URL = "https://covers.openlibrary.org/b/id/12345.jpg";
  private static final String TEST_AUTHOR = "F. Scott Fitzgerald";
  private static final String TEST_ISBN = "9780743273565";
  private static final String TEST_PUBLISH_DATE = "1925-04-10";
  private static final String TEST_DESCRIPTION = "A novel set in the Jazz Age";
  private static final String TEST_WORKSID = "OL468431W";

  @Before
  public void setUp() {
    bookItem = new MyBookItem(
        TEST_TITLE,
        TEST_IMAGE_URL,
        TEST_AUTHOR,
        TEST_ISBN,
        TEST_PUBLISH_DATE,
        TEST_DESCRIPTION
    );
  }

  // Constructor Tests
  @Test
  public void testConstructor_createsObjectWithCorrectValues() {
    assertEquals(TEST_TITLE, bookItem.getTitle());
    assertEquals(TEST_IMAGE_URL, bookItem.getImageUrl());
    assertEquals(TEST_AUTHOR, bookItem.getAuthor());
    assertEquals(TEST_ISBN, bookItem.getIsbn());
    assertEquals(TEST_PUBLISH_DATE, bookItem.getPublishDate());
    assertEquals(TEST_DESCRIPTION, bookItem.getDescription());
  }

  @Test
  public void testConstructor_withNullValues() {
    MyBookItem nullBookItem = new MyBookItem(null, null, null, null, null, null);
    assertNull(nullBookItem.getTitle());
    assertNull(nullBookItem.getImageUrl());
    assertNull(nullBookItem.getIsbn());
    assertNull(nullBookItem.getPublishDate());
    assertNull(nullBookItem.getDescription());
  }

  // Title Tests
  @Test
  public void testGetTitle_returnsCorrectValue() {
    assertEquals(TEST_TITLE, bookItem.getTitle());
  }

  @Test
  public void testSetTitle_updatesValue() {
    String newTitle = "1984";
    bookItem.setTitle(newTitle);
    assertEquals(newTitle, bookItem.getTitle());
  }

  @Test
  public void testSetTitle_withNull() {
    bookItem.setTitle(null);
    assertNull(bookItem.getTitle());
  }

  @Test
  public void testSetTitle_withEmptyString() {
    bookItem.setTitle("");
    assertEquals("", bookItem.getTitle());
  }

  // ImageUrl Tests
  @Test
  public void testGetImageUrl_returnsCorrectValue() {
    assertEquals(TEST_IMAGE_URL, bookItem.getImageUrl());
  }

  @Test
  public void testSetImageUrl_updatesValue() {
    String newUrl = "https://example.com/new-image.jpg";
    bookItem.setImageUrl(newUrl);
    assertEquals(newUrl, bookItem.getImageUrl());
  }

  @Test
  public void testSetImageUrl_withNull() {
    bookItem.setImageUrl(null);
    assertNull(bookItem.getImageUrl());
  }

  // Author Tests
  @Test
  public void testGetAuthor_returnsCorrectValue() {
    assertEquals(TEST_AUTHOR, bookItem.getAuthor());
  }

  @Test
  public void testSetAuthor_updatesValue() {
    String newAuthor = "George Orwell";
    bookItem.setAuthor(newAuthor);
    assertEquals(newAuthor, bookItem.getAuthor());
  }

  @Test
  public void testGetAuthor_withEmptyString_returnsEmptyString() {
    bookItem.setAuthor("");
    assertEquals("", bookItem.getAuthor());
  }

  @Test(expected = NullPointerException.class)
  public void testGetAuthor_withNull_throwsNullPointerException() {
    bookItem.setAuthor(null);
    bookItem.getAuthor(); // Should throw NPE when checking isEmpty()
  }

  @Test
  public void testSetAuthor_withWhitespace() {
    bookItem.setAuthor("   ");
    assertEquals("   ", bookItem.getAuthor());
  }

  // ISBN Tests
  @Test
  public void testGetIsbn_returnsCorrectValue() {
    assertEquals(TEST_ISBN, bookItem.getIsbn());
  }

  @Test
  public void testSetIsbn_updatesValue() {
    String newIsbn = "9780451524935";
    bookItem.setIsbn(newIsbn);
    assertEquals(newIsbn, bookItem.getIsbn());
  }

  @Test
  public void testSetIsbn_withNull() {
    bookItem.setIsbn(null);
    assertNull(bookItem.getIsbn());
  }

  // Publish Date Tests
  @Test
  public void testGetPublishDate_returnsCorrectValue() {
    assertEquals(TEST_PUBLISH_DATE, bookItem.getPublishDate());
  }

  @Test
  public void testSetPublishDate_updatesValue() {
    String newDate = "1949-06-08";
    bookItem.setPublishDate(newDate);
    assertEquals(newDate, bookItem.getPublishDate());
  }

  @Test
  public void testSetPublishDate_withNull() {
    bookItem.setPublishDate(null);
    assertNull(bookItem.getPublishDate());
  }

  @Test
  public void testGetPublishedDate_returnsPublishDate() {
    assertEquals(TEST_PUBLISH_DATE, bookItem.getPublishedDate());
  }

  @Test
  public void testGetPublishedDate_matchesGetPublishDate() {
    assertEquals(bookItem.getPublishDate(), bookItem.getPublishedDate());
  }

  // Description Tests
  @Test
  public void testGetDescription_returnsCorrectValue() {
    assertEquals(TEST_DESCRIPTION, bookItem.getDescription());
  }

  @Test
  public void testSetDescription_updatesValue() {
    String newDescription = "A dystopian novel";
    bookItem.setDescription(newDescription);
    assertEquals(newDescription, bookItem.getDescription());
  }

  @Test
  public void testSetDescription_withNull() {
    bookItem.setDescription(null);
    assertNull(bookItem.getDescription());
  }

  @Test
  public void testSetDescription_withLongText() {
    String longDescription = "A".repeat(10000);
    bookItem.setDescription(longDescription);
    assertEquals(longDescription, bookItem.getDescription());
  }
}
