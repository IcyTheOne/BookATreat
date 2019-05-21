package com.example.bookatreat.Customer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.bookatreat.DataBaseHandler.emailCredentials;


public class CustomerListResFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String KEY_NAME = "Name";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurants = db.collection("restaurants");


    private static final String TAG = "CustomerListResFrag";

    CustomerExampleAdapter adapter;

    private String resName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list_res, container, false);

        // Find views
        final ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);
        final ImageButton mapButton = view.findViewById(R.id.mapsBtn);

        // DU ARBEJDER HER

        final ArrayList<String> resList = new ArrayList<>();

        restaurants.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot documentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                resList.clear();

                for (DocumentSnapshot doc : documentSnapshots) {
                    resList.add(doc.getString(KEY_NAME));
                }
            }
        });

        adapter = new CustomerExampleAdapter(resList);
        mRecyclerView.setAdapter(adapter);

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_selectable_list_item, resList);
        adapter.notifyDataSetChanged();

        mRecyclerView = view.findViewById(R.id.restaurantList);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        //mAdapter = new CustomerExampleAdapter(resList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setAdapter(mAdapter);

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

        // Go to Maps View
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_cus, new MapsViewFrag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

    return view;
    }

    public void getUserDoc() {

        DocumentReference docRef = db.collection("restaurants").document(emailCredentials);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        resName = document.getString(KEY_NAME);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void fillSearchList() {

    }

}
