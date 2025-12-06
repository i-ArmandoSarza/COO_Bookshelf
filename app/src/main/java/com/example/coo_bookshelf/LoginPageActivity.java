package com.example.coo_bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coo_bookshelf.databinding.ActivityLoginPageBinding;

public class LoginPageActivity extends AppCompatActivity {

    // Through binding, we can access all views in activity_login_page.xml.
    private ActivityLoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate creates the screen from the XML layout.
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        // Shows the layout on the screen
        setContentView(binding.getRoot());

        // Action for login button when clicked.
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a plan to go to MainActivity after login.
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(),
                        0);
                // TODO: Replace with actual logged in user ID
                startActivity(intent);
            }
        });
    }

    /** Lets us jump to this activity from other activities */
    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginPageActivity.class);
    }

}