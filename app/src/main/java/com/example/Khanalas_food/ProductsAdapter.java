package com.example.Khanalas_food;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Product> mProducts;

    public ProductsAdapter() {
    }
    // Pass in the contact array into the constructor
    public ProductsAdapter( List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View contactView = inflater.inflate(R.layout.grid_card, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        Log.d("gg", "createViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        Product product = mProducts.get(position);

        TextView pidTextView = holder.pidTextView;
        pidTextView.setText(Integer.toString(product.getPid()));
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(product.getName());
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(Integer.toString(product.getPrice()));

        Log.d("gg", "bindViewHolder");
    }

    @Override
    public int getItemCount() {
        if (mProducts == null) return 0;
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pidTextView;
        public TextView nameTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            pidTextView = (TextView) itemView.findViewById(R.id.pid);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            priceTextView = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
