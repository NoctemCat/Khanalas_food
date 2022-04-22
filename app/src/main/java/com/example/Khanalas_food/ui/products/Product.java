package com.example.Khanalas_food.ui.products;

import java.util.ArrayList;

public class Product {
    private int mPid;
    private String mName;
    private int mCount;
    private int mPrice;
    private String mDescription;
    private String mType;

    public Product(int pid, String type, String name, int price, int count) {
        mPid = pid;
        mName = name;
        mPrice = price;
        mType = type;
        mCount = count;
    }

    public int getPid() {
        return mPid;
    }
    public String getName() {
        return mName;
    }
    public int getCount() {
        return mCount;
    }
    public int getPrice() {
        return mPrice;
    }
    public String getDescription() {
        return mDescription;
    }
    public String getType() { return mType;}

    public static ArrayList<Product> createProductsList(ArrayList<Product> products) {
        return products;
    }
}
