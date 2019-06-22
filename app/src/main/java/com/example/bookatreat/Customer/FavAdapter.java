package com.example.bookatreat.Customer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookatreat.R;
import com.example.bookatreat.Restaurants;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ExampleViewHolder> {

    public FavoritesFrag favoritesFrag;
    private ArrayList<Restaurants> mFavList;
    private CustomerExampleAdapter.OnResClickListener mOnResClickListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public ImageView mImageView;
        public TextView mTextResName;
        public TextView mTextResDes;
        CustomerExampleAdapter.OnResClickListener onResClickListener;

        public ExampleViewHolder(@NonNull View itemView, CustomerExampleAdapter.OnResClickListener onResClickListener) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.customerStar);
            mTextResName = itemView.findViewById(R.id.RestaurantName);
            mTextResDes = itemView.findViewById(R.id.restaurantDes);
            this.onResClickListener = onResClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onResClickListener.onResClick(getAdapterPosition());
        }
    }

    public interface OnFavClickListener {
        void onFavClick(int position);
    }

    public FavAdapter(FavoritesFrag favoritesFrag, ArrayList<Restaurants> mFavList, CustomerExampleAdapter.OnResClickListener onResClickListener) {
        this.favoritesFrag = favoritesFrag;
        this.mFavList = mFavList;
        this.mOnResClickListener = onResClickListener;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(favoritesFrag.getContext());
        View view = layoutInflater.inflate(R.layout.customer_list_example_item2,parent,false);

        return new ExampleViewHolder(view, mOnResClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ExampleViewHolder holder, int  position) {
        holder.mTextResName.setText(mFavList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mFavList.size();
    }
}
