package com.example.bookatreat.Restaurant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookatreat.R;
import com.example.bookatreat.Tables;

import java.util.ArrayList;

public class RestExampleAdapter extends RecyclerView.Adapter<RestExampleAdapter.TableViewHolder> {

    public NewBookingFrag mNewBookingFrag;
    private ArrayList<Tables> mTablesList;
    private OnTableClickListener mOnTableClickListener;

    public static class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public ImageView mImageView;
        TextView mTableID;
        TextView mTableSize;
        OnTableClickListener mOnTableClickListener;

        public TableViewHolder(@NonNull View itemView, OnTableClickListener onTableClickListener) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.customerStar);
            mTableID = itemView.findViewById(R.id.tableID);
            mTableSize = itemView.findViewById(R.id.tableSize);
            this.mOnTableClickListener = onTableClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnTableClickListener.onTableClick(getAdapterPosition());
        }
    }

    public interface OnTableClickListener {
        void onTableClick(int position);
    }

    public RestExampleAdapter(NewBookingFrag newBookingFrag, ArrayList<Tables> mTablesList, OnTableClickListener onTableClickListener) {
        this.mNewBookingFrag = newBookingFrag;
        this.mTablesList = mTablesList;
        this.mOnTableClickListener = onTableClickListener;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mNewBookingFrag.getContext());
        View view = layoutInflater.inflate(R.layout.customer_list_example_item, parent, false);

        return new TableViewHolder(view, mOnTableClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        holder.mTableID.setText(mTablesList.get(position).getID());
    }

    @Override
    public int getItemCount() {
        return mTablesList.size();
    }

    public void filterList(ArrayList<Tables> filteredList) {
        mTablesList = filteredList;
        notifyDataSetChanged();
    }
}
