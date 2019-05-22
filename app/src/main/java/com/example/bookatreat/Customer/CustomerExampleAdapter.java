package com.example.bookatreat.Customer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookatreat.R;

import java.util.ArrayList;

public class CustomerExampleAdapter extends RecyclerView.Adapter<CustomerExampleAdapter.ExampleViewHolder> {
    private ArrayList<String> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        //public ImageView mImageView;
        public TextView mTextResName;
        public TextView mTextResDes;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.customerStar);
            mTextResName = itemView.findViewById(R.id.restaurantName);
            mTextResDes = itemView.findViewById(R.id.restaurantDes);
        }
    }

    public CustomerExampleAdapter(ArrayList<String> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int  position) {
        /*
        CustomerExampleItem currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextResName.setText(currentItem.getRestaurantName());
        holder.mTextResDes.setText(currentItem.getRestaurantCuisine());
        */
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
