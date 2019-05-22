package com.example.bookatreat.Customer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookatreat.R;
import com.example.bookatreat.Restaurants;

import java.util.ArrayList;

public class CustomerExampleAdapter extends RecyclerView.Adapter<CustomerExampleAdapter.ExampleViewHolder> {

    public CustomerListResFrag customerListResFrag;
    private ArrayList<Restaurants> mExampleList;

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

    public CustomerExampleAdapter(CustomerListResFrag customerListResFrag, ArrayList<Restaurants> mExampleList) {
        this.customerListResFrag = customerListResFrag;
        this.mExampleList = mExampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(customerListResFrag.getContext());
        View view = layoutInflater.inflate(R.layout.customer_list_example_item,parent,false);

        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int  position) {
        holder.mTextResName.setText(mExampleList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
