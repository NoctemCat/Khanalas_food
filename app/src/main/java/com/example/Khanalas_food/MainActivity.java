package com.example.Khanalas_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnViewProducts;
    Button btnNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Khanalas_food);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
        btnNewProduct = (Button) findViewById(R.id.btnCreateProduct);

        // view products click event
        btnViewProducts.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), ContentActivity.class);
            startActivity(i);
        });
    }
}