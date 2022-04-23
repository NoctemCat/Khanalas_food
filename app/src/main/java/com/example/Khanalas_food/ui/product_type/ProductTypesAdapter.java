package com.example.Khanalas_food.ui.product_type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.EmptyFragment;
import com.example.Khanalas_food.R;
import com.example.Khanalas_food.ui.products.ProductsFragment;

import java.util.List;

public class ProductTypesAdapter extends RecyclerView.Adapter<ProductTypesAdapter.ViewHolder> {
    private final List<ProductType> mProductTypes;

    // Pass in the contact array into the constructor
    public ProductTypesAdapter(List<ProductType> products) {
        mProductTypes = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View contactView = inflater.inflate(R.layout.fragment_item, viewGroup, false);
        contactView.setOnClickListener(view -> {
            TextView tv = (TextView) view.findViewById(R.id.productTypeBDName);
            Bundle bundle = new Bundle();
            bundle.putString("product_type", tv.getText().toString());

            FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.nav_host_fragment_content_content, new EmptyFragment())
                    .commit();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.nav_host_fragment_content_content, ProductsFragment.class, bundle)
                    .commit();
        });

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductType product = mProductTypes.get(position);

        TextView bdNameTV = holder.bdNameTV;
        bdNameTV.setText(product.getBDName());
        TextView NameTV = holder.NameTV;
        NameTV.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        if (mProductTypes == null) return 0;
        return mProductTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bdNameTV;
        public TextView NameTV;


        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            bdNameTV = (TextView) itemView.findViewById(R.id.productTypeBDName);
            NameTV = (TextView) itemView.findViewById(R.id.productTypeName);
        }
    }
}
