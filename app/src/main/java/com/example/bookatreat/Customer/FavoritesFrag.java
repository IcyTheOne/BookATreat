package com.example.bookatreat.Customer;

import android.media.Image;
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
import android.widget.EditText;
import android.widget.ImageButton;
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

public class FavoritesFrag extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomerExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<Restaurants> mList;
    private ArrayList<String> mFavArrList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurants = db.collection("restaurants");


    private static final String TAG = "CustomerListResFrag";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        // Find IDs
        final ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);

        // Initialize ArrayList
        mFavArrList = new ArrayList<>();
//        ArrayList<Restaurants> favExList = mList.getmExampleList();

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



        return view;
    }
    private void setUpFireBase(){
        db = FirebaseFirestore.getInstance();
    }
}
