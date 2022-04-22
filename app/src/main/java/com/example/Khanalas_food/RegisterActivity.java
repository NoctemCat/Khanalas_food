package com.example.Khanalas_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonGoToLogin = (Button) findViewById(R.id.buttonGoLogin);

        buttonRegister.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), ContentActivity.class);
            startActivity(i);
        });

        buttonGoToLogin.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        });
    }
}