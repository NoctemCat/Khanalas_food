package com.example.Khanalas_food;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.Khanalas_food.databinding.ActivityContentBinding;
import com.example.Khanalas_food.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppBarContent extends AppCompatActivity {
    private ActivityContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Dd", "inside");
        binding = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navbottomView = findViewById(R.id.nav_bottom_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_products, R.id.nav_delivery, R.id.nav_cart)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_content);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navbottomView, navController);
//        navbottomView.setOnNavigationItemSelectedListener
        navbottomView.setOnItemSelectedListener(item->{
            Log.d("Dd", item.toString());
            return true;
        });
    }
}
