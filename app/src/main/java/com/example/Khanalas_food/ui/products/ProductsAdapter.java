package com.example.Khanalas_food.ui.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private final List<Product> mProducts;

    // Pass in the contact array into the constructor
    public ProductsAdapter( List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View contactView = inflater.inflate(R.layout.grid_card, viewGroup, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        Product product = mProducts.get(position);

        TextView pidTextView = holder.pidTextView;
        pidTextView.setText(Integer.toString(product.getPid()));
        TextView typeTextView = holder.typeTextView;
        typeTextView.setText(product.getType());
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(product.getName());
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(Integer.toString(product.getPrice()) + "руб");
        TextView countTextView = holder.countTextView;
        countTextView.setText("В наличии: "+Integer.toString(product.getCount()));
    }

    @Override
    public int getItemCount() {
        if (mProducts == null) return 0;
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pidTextView;
        public TextView typeTextView;
        public TextView nameTextView;
        public TextView priceTextView;
        public TextView countTextView;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            pidTextView = (TextView) itemView.findViewById(R.id.pid);
            typeTextView = (TextView) itemView.findViewById(R.id.type);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            priceTextView = (TextView) itemView.findViewById(R.id.price);
            countTextView = (TextView) itemView.findViewById(R.id.count);
        }
    }
}
