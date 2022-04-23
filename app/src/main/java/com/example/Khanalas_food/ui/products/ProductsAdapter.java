package com.example.Khanalas_food.ui.products;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Khanalas_food.ContentActivity;
import com.example.Khanalas_food.R;
import com.example.Khanalas_food.RequestHandler;
import com.example.Khanalas_food.SharedPrefManager;
import com.example.Khanalas_food.URLs;
import com.example.Khanalas_food.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

        View contactView = inflater.inflate(R.layout.fragment_product_card, viewGroup, false);
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
        priceTextView.setText(Integer.toString(product.getPrice()) + " руб");
        TextView countTextView = holder.countTextView;
        countTextView.setText("В наличии: "+Integer.toString(product.getCount()) + " шт");

        User user = SharedPrefManager.getInstance(pidTextView.getContext()).getUser();
        holder.addToCart.setOnClickListener(view -> {
            //if it passes all the validations
            class AddToCart extends AsyncTask<Void, Void, String> {

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user_id", Integer.toString(user.getId()));
                    params.put("prod_id", pidTextView.getText().toString());
                    params.put("prod_name", nameTextView.getText().toString());
                    params.put("count", "1");

                    //returing the response
                    return requestHandler.sendPostRequest(URLs.URL_ADD_TO_CART, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //hiding the progressbar after completion

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(pidTextView.getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(pidTextView.getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //executing the async task
            AddToCart atc = new AddToCart();
            atc.execute();
        });
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
        public Button addToCart;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            pidTextView = (TextView) itemView.findViewById(R.id.pid);
            typeTextView = (TextView) itemView.findViewById(R.id.type);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            priceTextView = (TextView) itemView.findViewById(R.id.price);
            countTextView = (TextView) itemView.findViewById(R.id.count);
            addToCart = (Button) itemView.findViewById(R.id.btn_buy);
        }
    }
}
