package com.example.Khanalas_food.ui.product_type;

public class ProductType {
    private int mId;
    private String mBDName;
    private String mName;

    public ProductType(int id, String bdName, String name){
        this.mId = id;
        this.mBDName = bdName;
        this.mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getBDName() {
        return mBDName;
    }

    public String getName() {
        return mName;
    }
}
