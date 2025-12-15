package com.example.coo_bookshelf.bookseach;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coo_bookshelf.R;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.database.entities.Book;
import com.example.coo_bookshelf.mybooks.MyBookItem;
import com.example.coo_bookshelf.services.OpenLibraryService;
import com.example.coo_bookshelf.services.model.WorksApiResponse;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBookSearchListFragment extends Fragment {

  private static final String BASE_URL = "https://openlibrary.org/";
  private BookshelfRepository repository;
  private int USERID;
  private ArrayList<MyBookItem> myBookItems;
  private MyBookSearchAdapter adapter;
  private OpenLibraryService olsService;

  public MyBookSearchListFragment(int userId, ArrayList<MyBookItem> myBookItems) {
    super(R.layout.my_book_search_list_fragment);
    this.USERID = userId;
    this.myBookItems = myBookItems;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView recyclerView = view.findViewById(R.id.myBookSearchRecyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    repository = BookshelfRepository.getRepository(requireActivity().getApplication());

    // TODO: Get the Description from the /works api call.

    // Setup up the data and the items that are selected
    adapter = new MyBookSearchAdapter(myBookItems, selectedItem -> {
      Book bookToSave = new Book(
          this.USERID,
          selectedItem.getTitle(),
          selectedItem.getAuthor(),
          selectedItem.getAuthorKey(),
          selectedItem.getWorksId()
      );
      bookToSave.setImageUrl(selectedItem.getImageUrl());
      bookToSave.setIsbn(selectedItem.getIsbn());
      bookToSave.setFirstPublishedYear(selectedItem.getPublishDate());

      initRetrofit(); //init the OpenLibrary service to query the last parts the book info we need
      // Added a call back because we need to wait to get the book details before we can save the record
      getWorksData(bookToSave, new BookDetailsCallback() {
        @Override
        public void onDetailsFetched(Book updatedBook) {
          // Save the book after we get the works data from the endpoint.
          repository.insert(updatedBook);
          Toast.makeText(requireContext(), "Book added to your shelf", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String message) {
          // Handle the error, for example by showing a toast
          Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        }
      });

    });

    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }

  private void getWorksData(Book selectedBook, BookDetailsCallback callback) {
    Call<WorksApiResponse> call = olsService.searchByWorksId(selectedBook.getWorksId());
    call.enqueue(new Callback<WorksApiResponse>() {
      @Override
      public void onResponse(Call<WorksApiResponse> call, Response<WorksApiResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
          WorksApiResponse worksApiResponse = response.body();

          // Use a mapper or handle logic here if we start grabbing more fields from the works end point
          var desc =
              (worksApiResponse.getDescription() != null && !worksApiResponse.getDescription()
                  .isEmpty())
                  ? worksApiResponse.getDescription()
                  : "No description available.";

          selectedBook.setDescription(desc);

          // Use the callback to return the updated book object
          callback.onDetailsFetched(selectedBook);

        } else {
          // On a server error response, call the error callback
          callback.onError("Failed to get title details. Code: " + response.code());
        }
      }
      @Override
      public void onFailure(Call<WorksApiResponse> call, Throwable t) {
        // On a network failure, call the error callback
        Log.e("MyBookSearchListFragment", "Network request failed: " + t.getMessage());
        callback.onError("Failed to retrieve book works details.");
      }
    });
  }

  private void initRetrofit() {
    // TODO: move this so we aren't copy pasta this method.
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(Level.BODY); // set your desired log level

    OkHttpClient client = new Builder()     // Added the logging interceptor to the OkHttpClient.
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

  // Callback interface for handling book details
  public interface BookDetailsCallback {

    void onDetailsFetched(Book updatedBook);

    void onError(String message);
  }
}