package com.example.Khanalas_food.ui.showproducts;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.Product;
import com.example.Khanalas_food.ProductsAdapter;
import com.example.Khanalas_food.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllProductsFragment extends Fragment {
    // url to get all products list
    private static final String url_all_products = "http://192.168.0.62:81/get_all_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";

    // products JSONArray
    JSONArray productsJSON = null;
    private MutableLiveData<ArrayList<Product>> products = new MutableLiveData<ArrayList<Product>>();

    RecyclerView rvProducts;

    public AllProductsFragment() {
        super(R.layout.fragment_all_products);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);
        rvProducts = (RecyclerView) view.findViewById(R.id.rvProducts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rvProducts.setLayoutManager(gridLayoutManager);
        rvProducts.setAdapter(new ProductsAdapter(products.getValue()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Lookup the recyclerview in activity layout
//        new AllProducts.LoadAllProducts().execute();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            Log.d("Get line: ", "Beginning async");

            JSONObject json = null;
            try {
                URL url = new URL(url_all_products);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                int responseCode = connection.getResponseCode();
                Log.d("Get line: ", "GET Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    json = new JSONObject(response.toString());
                } else {
                    Log.d("Get line: ", "GET request didn't work");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Product> prods = new ArrayList<Product>();
            // Check your log cat for JSON response
            if (json != null) {
                try {
                    Log.d("All Products: ", json.toString());
                    // Checking for SUCCESS TAG
                    int success;
                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of Products
                        productsJSON = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Products
                        for (int i = 0; i < productsJSON.length(); i++) {
                            JSONObject c = productsJSON.getJSONObject(i);

                            // Storing each json item in variable
                            int id = c.getInt(TAG_PID);
                            String name = c.getString(TAG_NAME);
                            int price = c.getInt(TAG_PRICE);

                            prods.add(new Product(id, name, price));
                        }
                    } else {
                        Log.d("All Products: ", "no products found");
                    }
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("All Products: ", "json is null");
            }

            handler.post(() -> {
                //UI Thread work here
                rvProducts.setAdapter(new ProductsAdapter(prods));
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rvProducts.setAdapter(null);
        rvProducts = null;
    }
}
