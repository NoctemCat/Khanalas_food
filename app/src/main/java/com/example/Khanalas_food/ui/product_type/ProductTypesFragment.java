package com.example.Khanalas_food.ui.product_type;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Khanalas_food.ContentActivity;
import com.example.Khanalas_food.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ProductTypesFragment extends Fragment {

    public ProductTypesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        RecyclerView rvProductTypes = (RecyclerView) view.findViewById(R.id.product_type_rc);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        rvProductTypes.setLayoutManager(gridLayoutManager);

        ArrayList<ProductType> productTypes = new ArrayList<>();
        productTypes.add(new ProductType(0, "milk", "Молочные продукты"));
        productTypes.add(new ProductType(1, "meat", "Мясные полуфабрикаты"));
        productTypes.add(new ProductType(2, "fruit", "Офощная продукция"));
        productTypes.add(new ProductType(3, "fish", "Рыбные продукты"));
        productTypes.add(new ProductType(4, "wild", "Продукты из дикоросов"));

        rvProductTypes.setAdapter(new ProductTypesAdapter(productTypes));
        ((ContentActivity)getActivity()).setmCurrentFragId(R.layout.fragment_products);
        return view;
    }
}