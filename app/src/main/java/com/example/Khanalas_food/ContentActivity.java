package com.example.Khanalas_food;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.Khanalas_food.databinding.ActivityContentBinding;
import com.example.Khanalas_food.ui.cart.CartFragment;
import com.example.Khanalas_food.ui.delivery.DeliveryFragment;
import com.example.Khanalas_food.ui.gallery.GalleryFragment;
import com.example.Khanalas_food.ui.home.HomeFragment;
import com.example.Khanalas_food.ui.showproducts.AllProductsFragment;
import com.example.Khanalas_food.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ContentActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityContentBinding binding;
    private int mCurrentFragId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setSupportActionBar(binding.appBarContent.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        Toolbar toolbar = binding.appBarContent.toolbar;

        // set custom toggle menu to open and close drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        //set menu icon
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_camera);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                    new HomeFragment()).commit();
            mCurrentFragId = R.id.nav_home;
        }

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(item->{
            if (mCurrentFragId == item.getItemId()){
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new HomeFragment()).commit();
                    drawer.closeDrawers();
                    break;
                case R.id.nav_gallery:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new GalleryFragment()).commit();
                    drawer.closeDrawers();
                    break;
                case R.id.nav_slideshow:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_content,
                            new SlideshowFragment()).commit();
                    drawer.closeDrawers();
                    break;
                default:
                    break;
            }
            mCurrentFragId = item.getItemId();
            return true;
        });

        BottomNavigationView bottom = binding.appBarContent.navBottomView;
        bottom.setOnItemSelectedListener(item->{
            if (mCurrentFragId == item.getItemId()){
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_products:
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.nav_host_fragment_content_content, new ScrollingFragment())
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.nav_host_fragment_content_content, AllProductsFragment.class, null)
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