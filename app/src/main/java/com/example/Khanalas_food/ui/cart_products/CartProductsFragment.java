package com.example.Khanalas_food.ui.cart_products;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Khanalas_food.R;
import com.example.Khanalas_food.RequestHandler;
import com.example.Khanalas_food.SharedPrefManager;
import com.example.Khanalas_food.URLs;
import com.example.Khanalas_food.User;
import com.example.Khanalas_food.ui.products.Product;
import com.example.Khanalas_food.ui.products.ProductsAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.api.client.util.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartProductsFragment extends Fragment {

    // JSON Node names
    private static final String TAG_ERROR = "error";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CART_PRODUCTS = "cart_products";

    private static final String TAG_ID = "cart_id";
    private static final String TAG_PROD_ID = "prod_id";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_PROD_NAME = "prod_name";
    private static final String TAG_COUNT = "count";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_TIME = "time";

    // products JSONArray
//    JSONArray productsJSON = null;
//    private ArrayList<Product> products = new ArrayList<Product>();

//    CartProductsAdapter mAdapter;
    RecyclerView cartProductsView;
    MaterialButton btnBuyAll;

    public CartProductsFragment() {
        super(R.layout.fragment_cart_product);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_product, container, false);
        cartProductsView = (RecyclerView) view.findViewById(R.id.cart_product_rc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cartProductsView.setLayoutManager(layoutManager);

        btnBuyAll = (MaterialButton) view.findViewById(R.id.btn_buy_all);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        User user = SharedPrefManager.getInstance(getContext()).getUser();

        btnBuyAll.setOnClickListener(viewbtn->{
            class BuyAllCart extends AsyncTask<Void, Void, String> {

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user_id", Integer.toString(user.getId()));

                    //returing the response
                    return requestHandler.sendPostRequest(URLs.URL_BUY_CART, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    try {
                        JSONObject obj = new JSONObject(s);

                        cartProductsView.setAdapter(new CartProductsAdapter(new ArrayList<CartProduct>()));

                        final Activity activity = getActivity();
                        if(activity != null) {
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //executing the async task
            BuyAllCart bac = new BuyAllCart();
            bac.execute();
        });


        class AddToCart extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", Integer.toString(user.getId()));

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GET_CART, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion

                try {
                    //converting response to json object
                    Log.d("what", s);
                    JSONObject obj = new JSONObject(s);
//
                    JSONArray arr = obj.getJSONArray(TAG_CART_PRODUCTS);
//                    JSONArray arr = new JSONArray(s);

                    ArrayList<CartProduct> cartProducts = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject c = arr.getJSONObject(i);

                        // Storing each json item in variable
                        int id = c.getInt(TAG_ID);
                        int userId = c.getInt(TAG_USER_ID);
                        int prodID = c.getInt(TAG_PROD_ID);
                        String prodName = c.getString(TAG_PROD_NAME);
                        int count = c.getInt(TAG_COUNT);
                        String address = c.getString(TAG_ADDRESS);
                        String time = c.getString(TAG_TIME);

                        CartProduct cartPro =new CartProduct(id, userId, prodID, prodName, count, address, time);
                        cartProducts.add(cartPro);
//                        cartProductsView.setAdapter(new );
//                        mAdapter.notifyItemChanged(cartProducts.size()-1);
                    }
                    cartProductsView.setAdapter(new CartProductsAdapter(cartProducts));

                    final Activity activity = getActivity();
                    if(activity != null) {
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        AddToCart atc = new AddToCart();
        atc.execute();

        super.onViewCreated(view, savedInstanceState);
    }
}