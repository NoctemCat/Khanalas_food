package com.example.Khanalas_food.ui.products;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.R;
import com.example.Khanalas_food.URLs;

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

public class ProductsFragment extends Fragment {
    // url to get all products list

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_COUNT = "count";
    private static final String TAG_TYPE = "type";

    // products JSONArray
//    JSONArray productsJSON = null;
//    private ArrayList<Product> products = new ArrayList<Product>();

    RecyclerView rvProducts;
    String product_type = "";

    public ProductsFragment() {
        super(R.layout.fragment_products);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        product_type =  getArguments().getString("product_type");

        View view = inflater.inflate(R.layout.fragment_products, container, false);
        rvProducts = (RecyclerView) view.findViewById(R.id.rvProducts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rvProducts.setLayoutManager(gridLayoutManager);
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
                String urlstring = URLs.URL_ALL_PRODUCTS;
                if(!product_type.isEmpty()){
                    urlstring += "?type=";
                    urlstring += product_type;
                }

                URL url = new URL(urlstring);
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

            // Check your log cat for JSON response
            ArrayList<Product> products = new ArrayList<>();
            if (json != null) {
                try {
                    Log.d("All Products: ", json.toString());
                    // Checking for SUCCESS TAG
                    int success;
                    success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of Products
                        JSONArray productsJSON = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Products
                        for (int i = 0; i < productsJSON.length(); i++) {
                            JSONObject c = productsJSON.getJSONObject(i);

                            // Storing each json item in variable
                            int id = c.getInt(TAG_PID);
                            String name = c.getString(TAG_NAME);
                            int price = c.getInt(TAG_PRICE);
                            String type = c.getString(TAG_TYPE);
                            int count = c.getInt(TAG_COUNT);

                            Product pro =new Product(id,type, name, price, count);
                            products.add(pro);
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
                rvProducts.setAdapter(new ProductsAdapter(products));
            });
        });
    }
}
