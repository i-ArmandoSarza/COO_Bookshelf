package com.example.coo_bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coo_bookshelf.databinding.ActivityMyBookSearchBinding;
import com.example.coo_bookshelf.services.OpenLibraryService;
import com.example.coo_bookshelf.services.SearchApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBookSearchActivity extends AppCompatActivity {

  private static final String BASE_URL = "https://openlibrary.org/";
  private ActivityMyBookSearchBinding binding;
  private int userId;
  private OpenLibraryService service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMyBookSearchBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    userId = getIntent().getIntExtra("USER_ID", -1);
    if(userId == -1) {
      toastMaker("Error: no user ID.");
      finish();
      return;
    }
    getAuthorSearchResults("xx");
  }

  private void getAuthorSearchResults(String title) {

    // Added the HttpLoggingInterceptor to view the response in the logcat to troubleshoot json issues.
    // This is where you would also add the networkInterceptors to attach the api key to each request
    // for this app we are adding the headers in the OpenLibraryService.
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    // Added the logging interceptor to the OkHttpClient.
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(logging)
        .build();

    // Create a Retrofit instance with the base URL)
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) //JSON converter
        .client(client) // Added OkHttpClient to the Retrofit instance.
        .build();

    OpenLibraryService service = retrofit.create(OpenLibraryService.class);
    Call<SearchApiResponse> call = service.searchByTitle("The Kite runner", "en");

    call.enqueue(new Callback<SearchApiResponse>() {
      @Override
      public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
        var b = response;
        if (response.isSuccessful()) {
          SearchApiResponse searchApiResponse = response.body();
        }
        else {
          var msg = "Error found is : " + response.body();
          toastMaker(msg);
        }
      }

      @Override
      public void onFailure(Call<SearchApiResponse> call, Throwable t) {
        // setting text to our text view when
        // we get error response from API.
        //responseTV.setText("Error found is : " + t.getMessage());
        var msg = "Error found is : " + t.getMessage();
        toastMaker(msg);
      }
    });
  }
  // setup intent for MyBookActivity
  static Intent MyBookSearchActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MyBookSearchActivity.class);
    intent.putExtra("USER_ID", userId);
    return intent;
  }
  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}