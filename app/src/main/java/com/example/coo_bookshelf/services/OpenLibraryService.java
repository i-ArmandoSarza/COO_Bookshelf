package com.example.coo_bookshelf.services;


import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenLibraryService {



  @Headers({"Accept: application/json"})
  @GET("/search")
    public Call<SearchApiResponse> searchByTitle(@Query("title") String title, @Query("lang") String lang);

  @Headers({"Accept: application/json"})
  @GET("/search")
  public Call<SearchApiResponse> searchByAuthor(@Query("author.json") String author, @Query("lang") String lang);

  @Headers({"Accept: application/json"})
  @GET("/search")
  public Call<SearchApiResponse> searchByTitleAndAuthor(@Query("title.json") String title,
      @Query("author") String author, @Query("lang") String lang);
}
