package com.example.Khanalas_food.ui.cart_products;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder> {
    private final List<CartProduct> mCartProducts;

    // Pass in the contact array into the constructor
    public CartProductsAdapter(List<CartProduct> cartProducts) {
        mCartProducts = cartProducts;
    }

    @NonNull
    @Override
    public CartProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View contactView = inflater.inflate(R.layout.fragment_cart_product_item, viewGroup, false);
        return new ViewHolder(contactView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartProductsAdapter.ViewHolder holder, int position) {
        CartProduct product = mCartProducts.get(position);

        TextView cartIdTextView = holder.cartIdTextView;
        cartIdTextView.setText(Integer.toString(product.getId()));
        TextView userIdTextView = holder.userIdTextView;
        userIdTextView.setText(Integer.toString(product.getUserId()));
        TextView prodIdTextView = holder.prodIdTextView;
        prodIdTextView.setText(Integer.toString(product.getProdId()));
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(product.getProdName());
        TextView countEditText = holder.countEditText;
        countEditText.setText(Integer.toString(product.getCount()));
    }

    @Override
    public int getItemCount() {
        if (mCartProducts == null) return 0;
        return mCartProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cartIdTextView;
        public TextView userIdTextView;
        public TextView prodIdTextView;
        public TextView nameTextView;
        public TextInputEditText countEditText;
//
        public ViewHolder(View itemView) {
//            // Stores the itemView in a public final member variable that can be used
//            // to access the context from any ViewHolder instance.
            super(itemView);
//
            cartIdTextView = (TextView) itemView.findViewById(R.id.cartProductId);
            userIdTextView = (TextView) itemView.findViewById(R.id.cartProductUserId);
            prodIdTextView = (TextView) itemView.findViewById(R.id.cartProductProdId);
            nameTextView = (TextView) itemView.findViewById(R.id.cartProductName);
            countEditText = (TextInputEditText) itemView.findViewById(R.id.cartProductCountEdit);
        }
    }
}
