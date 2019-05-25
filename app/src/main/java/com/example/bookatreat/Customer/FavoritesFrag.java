package com.example.bookatreat.Customer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.R;
import com.example.bookatreat.Restaurants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavoritesFrag extends Fragment implements CustomerExampleAdapter.OnResClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavAdapter mAdapter;
    private ArrayList<Restaurants> mFavList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurants = db.collection("restaurants");


    private static final String TAG = "CustomerListResFrag";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        // Find IDs
        final ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);

        // Initialize ArrayList
        mFavList = new ArrayList<>();
        mFavList = CustomerListResFrag.getFavArrList();

        // Setup RecyclerView
        mRecyclerView = view.findViewById(R.id.favResList);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setUpFireBase();

        // Go to Settings
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_cus, new CustomerSettingsFrag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        mAdapter = new FavAdapter(FavoritesFrag.this, mFavList, this);
        mRecyclerView.setAdapter(mAdapter);

//        fillFavList();

        return view;
    }
    private void setUpFireBase(){
        db = FirebaseFirestore.getInstance();
    }

//    private void fillFavList() {
//        if(mFavList.size()>0){
//            mFavList.clear();
//        }
//        mAdapter = new FavAdapter(FavoritesFrag.this, mFavList, this);
//        db.collection("restaurants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for(DocumentSnapshot querySnapshot: task.getResult()){
//                    Restaurants restaurant = new Restaurants(querySnapshot.getString("Address"), querySnapshot.getString("Description"), querySnapshot.getString("Email"), querySnapshot.getString("Name"));
//                    mFavList.add(restaurant);
//                }
//                mRecyclerView.setAdapter(mAdapter);
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getContext(), "Problem ---1---", Toast.LENGTH_SHORT).show();
//                        Log.v("---1---", e.getMessage());
//                    }
//                });
//    }

    // The open Restaurants dialog method
    public void OpenRestaurantDialog(final int position) {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_res_view, null);

        final TextView resNameD = mView.findViewById(R.id.resNameDialog);
        final TextView resDesD = mView.findViewById(R.id.resDesDialog);
        final TextView resEmailD = mView.findViewById(R.id.resEmailDialog);
        final TextView resAddD = mView.findViewById(R.id.resAddressDialog);
        final ImageView favStar = mView.findViewById(R.id.favStar);

        resNameD.setText(mFavList.get(position).getName());
        resDesD.setText(mFavList.get(position).getDescription());
        resAddD.setText(mFavList.get(position).getAddress());
        resEmailD.setText(mFavList.get(position).getEmail());

        //On Click of Star Symbol
        favStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove position to FavoritesArray
//                Restaurants favRes = mFavList.get(position);
//                mFavList.add(favRes);
            }
        });


        myBuild.setView(mView);
        final AlertDialog dialog = myBuild.create();
        dialog.show();
    }

    @Override
    public void onResClick(int position) {
        Log.d(TAG, "onResClick: Clicked Pos - " + position);

        OpenRestaurantDialog(position);
    }
}






















