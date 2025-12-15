package com.example.coo_bookshelf.bookseach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.databinding.ActivityMyBookSearchBinding;
import com.example.coo_bookshelf.mybooks.MyBookItem;
import com.example.coo_bookshelf.services.BookMapper;
import com.example.coo_bookshelf.services.OpenLibraryService;
import com.example.coo_bookshelf.services.model.SearchApiResponse;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBookSearchActivity extends AppCompatActivity {

  private static final String COVER_IMG_URL = "https://covers.openlibrary.org/b/id";
  private static final String BASE_URL = "https://openlibrary.org/";
  private ActivityMyBookSearchBinding binding;
  private int userId;
  private OpenLibraryService olsService;

  private enum SearchType {
    TITLE,
    AUTHOR
  }

  private SearchType currentSearchType = SearchType.TITLE;
  private OpenLibraryService service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMyBookSearchBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    userId = getIntent().getIntExtra("USER_ID", -1);
    if (userId == -1) {
      toastMaker("Error: no user ID.");
      finish();
      return;
    }
    setupSearchTypeSpinner();
    setupSearchView();
    initRetrofit();

  }


  private void initRetrofit() {
    // Added the HttpLoggingInterceptor to view the response in the logcat to troubleshoot json issues.
    // This is where you would also add the networkInterceptors to attach the api key to each request
    // for this app we are adding the headers in the OpenLibraryService.
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY); // set your desired log level

    OkHttpClient client = new OkHttpClient.Builder()     // Added the logging interceptor to the OkHttpClient.
        .addInterceptor(logging)
        .build();

    // Create a Retrofit instance with the base URL.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) //JSON converter
        .client(client) // Added OkHttpClient to the Retrofit instance.
        .build();

    olsService = retrofit.create(OpenLibraryService.class);
  }

  private void setupSearchTypeSpinner() {
    String[] options = new String[]{"Title", "Author"};

    ArrayAdapter<String> adapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_spinner_item,
        options
    );

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    binding.spinnerSearchType.setAdapter(adapter);

    binding.spinnerSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(
          AdapterView<?> parent,
          View view,
          int position,
          long id
      ) {
        if (position == 0) {
          currentSearchType = SearchType.TITLE;
        } else {
          currentSearchType = SearchType.AUTHOR;
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // default to TITLE if nothing is selected
        currentSearchType = SearchType.TITLE;
      }
    });
  }

  private void setupSearchView() {
    SearchView searchView = binding.searchViewBooks;

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        if (query != null && !query.trim().isEmpty()) {
          String trimmed = query.trim(); // trim any accident white sapce.
          if (currentSearchType == SearchType.TITLE) {
            getTitleSearchResults(trimmed);
          } else {
            getAuthorSearchResults(trimmed);
          }
          searchView.clearFocus();
        }
        return true; // we handled it
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        // This would be fine searching the db, but the api has a low rate limit. We don't want to
        // fire off a bunch of request as someone types.
        // Log.i(MainActivity.TAG, "onQueryTextChange: " + newText);
        return false;
      }
    });
  }

  private void getTitleSearchResults(String title) {
    if (olsService == null) {
      toastMaker("Service not initialized");
      return;
    }

    // Adjust parameters based on your OpenLibraryService definition
    Call<SearchApiResponse> call = olsService.searchByTitle(title, "en");

    call.enqueue(new Callback<SearchApiResponse>() {
      @Override
      public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
        if (response.isSuccessful()) {
          SearchApiResponse searchApiResponse = response.body();
          ArrayList<MyBookItem> myBookItems = BookMapper.mapSearchResponseToMyBookItems(
              response.body());

          if (!myBookItems.isEmpty()) {
            showSearchResultsFragment(myBookItems);
          }
          toastMaker("Found results for title: " + title);
        } else {
          toastMaker("Title search error: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<SearchApiResponse> call, Throwable t) {
        toastMaker("Title search failed: " + t.getMessage());
      }
    });
  }

  private void getAuthorSearchResults(String author) {
    if (service == null) {
      toastMaker("Service not initialized");
      return;
    }

    // Adjust parameters based on your OpenLibraryService definition
    Call<SearchApiResponse> call = service.searchByAuthor(author, "en");

    call.enqueue(new Callback<SearchApiResponse>() {
      @Override
      public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
        if (response.isSuccessful()) {
          SearchApiResponse searchApiResponse = response.body();
          // TODO: update UI or fragment with the results
          toastMaker("Found results for author: " + author);
        } else {
          toastMaker("Author search error: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<SearchApiResponse> call, Throwable t) {
        toastMaker("Author search failed: " + t.getMessage());
      }
    });
  }

  private void showSearchResultsFragment(ArrayList<MyBookItem> myBookItems) {
    MyBookSearchListFragment fragment = new MyBookSearchListFragment(userId, myBookItems);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.myBookSearchFragmentContainerView, fragment)
        .commit();
  }

  // setup intent for MyBookActivity
  public static Intent MyBookSearchActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MyBookSearchActivity.class);
    intent.putExtra("USER_ID", userId);
    return intent;
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

}