package com.example.Khanalas_food.ui.cart_products;

import com.google.api.client.util.DateTime;

public class CartProduct {
    private int mId;
    private int mUserId;
    private int mProdId;
    private String mProdName;
    private int mCount;
    private String mAddress;
    private String mTime;

    public CartProduct(int id, int userid, int prodId, String prodName, int count, String address, String time){
        this.mId = id;
        this.mUserId = userid;
        this.mProdId = prodId;
        this.mProdName = prodName;
        this.mCount = count;
        this.mAddress = address;
        this.mTime = time;
    }

    public int getId() { return mId; }
    public int getUserId() { return mUserId;}
    public int getProdId() { return mProdId; }
    public String getProdName() { return mProdName; }
    public int getCount() { return mCount; }
    public String getAddress() { return mAddress; }
    public String getTime() { return mTime; }
}
