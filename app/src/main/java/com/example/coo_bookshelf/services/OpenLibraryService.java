package com.example.coo_bookshelf.services;


import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenLibraryService {

  String BASE_URL = "https://openlibrary.org/";

  @GET("search")
  @FormUrlEncoded
  public Call<SearchApiResponse> searchByTitle(@Query("title") String title);

  @GET("search")
  @FormUrlEncoded
  public Call<SearchApiResponse> searchByAuthor(@Query("author") String author);

  @GET("search")
  @FormUrlEncoded
  public Call<SearchApiResponse> searchByTitleAndAuthor(@Query("title") String title,
      @Query("author") String author);
}
