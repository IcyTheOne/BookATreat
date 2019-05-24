package com.example.bookatreat.Customer;

import android.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;

import com.example.bookatreat.R;

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
