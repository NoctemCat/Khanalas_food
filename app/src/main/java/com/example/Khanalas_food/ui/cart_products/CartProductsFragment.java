package com.example.Khanalas_food.ui.cart_products;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Khanalas_food.R;

public class CartProductsFragment extends Fragment {

    // JSON Node names
    private static final String TAG_ERROR = "error";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_CART_PRODUCTS = "cart_products";


    private static final String TAG_ID = "pid";
    private static final String TAG_PROD_ID = "prod_id";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_PROD_NAME = "prod_name";
    private static final String TAG_COUNT = "count";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_TIME = "time";

    // products JSONArray
//    JSONArray productsJSON = null;
//    private ArrayList<Product> products = new ArrayList<Product>();

    RecyclerView cpProducts;
    String product_type = "";

    public CartProductsFragment() {
        super(R.layout.fragment_cart_product);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_product, container, false);
        cpProducts = (RecyclerView) view.findViewById(R.id.cart_product_rc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cpProducts.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}