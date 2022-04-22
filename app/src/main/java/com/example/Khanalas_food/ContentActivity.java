package com.example.Khanalas_food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.Khanalas_food.databinding.ActivityContentBinding;
import com.example.Khanalas_food.ui.cart.CartFragment;
import com.example.Khanalas_food.ui.delivery.DeliveryFragment;
import com.example.Khanalas_food.ui.help.HelpFragment;
import com.example.Khanalas_food.ui.product_type.ProductTypeFragment;
import com.example.Khanalas_food.ui.settings.SettingsFragment;
import com.example.Khanalas_food.ui.about_us.AboutUsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ContentActivity extends AppCompatActivity {
    private int mCurrentFragId;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        com.example.Khanalas_food.databinding.ActivityContentBinding binding = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setSupportActionBar(binding.appBarContent.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        Toolbar toolbar = binding.appBarContent.toolbar;

        // set custom toggle menu to open and close drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        //set menu icon
        drawerToggle.setHomeAsUpIndicator(R.drawable.menumore);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));



        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .replace(R.id.nav_host_fragment_content_content, new EmptyFragment())
//                    .commit();
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.nav_host_fragment_content_content, AllProductsFragment.class, null)
//                    .commit();
                getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.nav_host_fragment_content_content, new ProductTypeFragment())
                    .commit();
            mCurrentFragId = R.id.nav_products;
        }

        NavigationView navigationView = binding.navView;
        BottomNavigationView bottom = binding.appBarContent.navBottomView;
        bottom.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(item->{
            if (mCurrentFragId == item.getItemId()){
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new SettingsFragment()).commit();
                    drawer.closeDrawers();
                    break;
                case R.id.nav_help:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new HelpFragment()).commit();
                    drawer.closeDrawers();
                    break;
                case R.id.nav_about_us:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new AboutUsFragment()).commit();
                    drawer.closeDrawers();
                    break;
                case R.id.nav_log_out:
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    break;
                default:
                    break;
            }
            mCurrentFragId = item.getItemId();
            return true;
        });

        bottom.setOnItemSelectedListener(item->{
            MenuItem nav_item = navigationView.getMenu().findItem(mCurrentFragId);
            if(nav_item != null){
                nav_item.setChecked(false);
            }
            if (mCurrentFragId == item.getItemId()){
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_products:
//                    getSupportFragmentManager().beginTransaction()
//                            .setReorderingAllowed(true)
//                            .replace(R.id.nav_host_fragment_content_content, new EmptyFragment())
//                            .commit();
//                    getSupportFragmentManager().beginTransaction()
//                            .setReorderingAllowed(true)
//                            .add(R.id.nav_host_fragment_content_content, AllProductsFragment.class, null)
//                            .commit();

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.nav_host_fragment_content_content, new ProductTypeFragment())
                            .commit();
                        drawer.closeDrawers();
                    break;
                case R.id.nav_delivery:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new DeliveryFragment()).commit();
                    drawer.closeDrawers();
                    break;
                case R.id.nav_cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new CartFragment()).commit();
                    drawer.closeDrawers();
                    break;
                default:
                    break;
            }
            mCurrentFragId = item.getItemId();
            return true;
        });
    }
}