package com.example.Khanalas_food;

import java.util.ArrayList;

public class Product {
    private int mPid;
    private String mName;
    private int mCount;
    private int mPrice;
    private String mDescription;

    public Product(int pid, String name, int price) {
        mPid = pid;
        mName = name;
        mPrice = price;
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

    public static ArrayList<Product> createProductsList(ArrayList<Product> products) {
//        ArrayList<Product> products = new ArrayList<Product>();
//        for (int i = 1; i <= numContacts; i++) {
//            products.add(new Product("Person " + ++lastContactId));
//        }
        return products;
    }
}
