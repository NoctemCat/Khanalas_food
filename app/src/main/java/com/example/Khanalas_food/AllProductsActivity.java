package com.example.Khanalas_food;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

//import org.apache.http.NameValuePair;
//import org.json.simple.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.Khanalas_food.ui.showproducts.AllProductsFragment;


public class AllProductsActivity extends AppCompatActivity {
    public AllProductsActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_holder, AllProductsFragment.class, null)
                    .commit();
        }
    }



}