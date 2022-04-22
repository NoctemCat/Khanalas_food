package com.example.Khanalas_food;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllProducts extends AppCompatActivity {
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
    ArrayList<Product> products = new ArrayList<>();

    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_products);

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
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    json = new JSONObject(response.toString());
                } else {
                    Log.d("Get line: ", "GET request didn't work");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

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

                        products.add(new Product(id, name, price));
                    }
                } else {
                    Log.d("All Products: ", "no products found");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                //UI Thread work here
                RecyclerView rvProducts = (RecyclerView) findViewById(R.id.rvProducts);
                ProductsAdapter adapter = new ProductsAdapter(products);
                rvProducts.setAdapter(adapter);
                rvProducts.setLayoutManager(new GridLayoutManager(context, 2));
            });
        });
    }
}
