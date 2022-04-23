package com.example.Khanalas_food.ui.cart_products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.R;
import com.example.Khanalas_food.RequestHandler;
import com.example.Khanalas_food.URLs;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
        int pos = position;
        CartProduct product = mCartProducts.get(pos);

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

        countEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (Integer.parseInt(s.toString()) <= 0){
                    holder.editMinusTextView.setClickable(false);
                    holder.editPlusTextView.setClickable(false);
                    countEditText.setEnabled(false);

                    deleteItem(cartIdTextView.getContext(), product.getId());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {}
        });

        holder.editMinusTextView.setOnClickListener(view->{
            int nextPos = Integer.parseInt(countEditText.getText().toString()) - 1;
            product.setCount(nextPos);
            countEditText.setText(Integer.toString(product.getCount()));
        });

        holder.editPlusTextView.setOnClickListener(view->{
            product.setCount(Integer.parseInt(countEditText.getText().toString()) + 1);
            countEditText.setText(Integer.toString(product.getCount()));

        });

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
        public TextView editMinusTextView;
        public TextView editPlusTextView;

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
            editMinusTextView = (TextView) itemView.findViewById(R.id.cartProductCountMinus);
            editPlusTextView = (TextView) itemView.findViewById(R.id.cartProductCountPlus);
        }
    }

    private void deleteItem(Context context, int cart_id){

        class DeleteFromCart extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("cart_id", Integer.toString(cart_id));

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_DELETE_CART_ITEM, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
//
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        DeleteFromCart dfc = new DeleteFromCart();
        dfc.execute();
    }
}
