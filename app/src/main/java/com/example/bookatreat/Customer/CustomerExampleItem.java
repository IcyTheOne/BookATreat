package com.example.bookatreat.Customer;

public class CustomerExampleItem {
    private int mImageResource;
    private String mRestaurantName, mRestaurantCuisine;

    public CustomerExampleItem(int imageRes, String resName, String resCuis){
        mImageResource = imageRes;
        mRestaurantName = resName;
        mRestaurantCuisine = resCuis;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public String getRestaurantCuisine() {
        return mRestaurantCuisine;
    }
}
