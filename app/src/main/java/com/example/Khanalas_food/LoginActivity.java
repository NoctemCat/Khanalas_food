package com.example.Khanalas_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Khanalas_food_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ContentActivity.class));
            return;
        }

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        Button buttonGoToRegister = (Button) findViewById(R.id.buttonGoToRegister);

        buttonLogin.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), ContentActivity.class);
            startActivity(i);
        });

        buttonGoToRegister.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        });
    }
}