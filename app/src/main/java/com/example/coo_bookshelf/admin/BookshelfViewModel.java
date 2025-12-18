package com.example.coo_bookshelf.admin;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.coo_bookshelf.database.BookshelfRepository;
import com.example.coo_bookshelf.database.entities.User;
import java.util.List;

public class BookshelfViewModel extends AndroidViewModel {

  private static BookshelfRepository repository;

  private final LiveData<List<User>> allUsers;

  public BookshelfViewModel(Application application) {
    super(application);
    repository = BookshelfRepository.getRepository(application);
    allUsers = repository.getAllUsersLiveData();
  }

  public LiveData<List<User>> getAllUsers() {
    return allUsers;
  }

  public void removeUser(User user) {
    repository.delete(user);
  }
}
