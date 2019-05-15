package com.example.bookatreat.Customer;

public class CustomerExampleItem {
    private int mImageResource;
    private String mRestaurantName;
    private String mRestaurantDes;

    public CustomerExampleItem(int imageRes, String resName, String resDes){
        mImageResource = imageRes;
        mRestaurantName = resName;
        mRestaurantDes = resDes;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public String getRestaurantDes() {
        return mRestaurantDes;
    }
}
