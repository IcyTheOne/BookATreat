package com.example.bookatreat.Customer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.example.bookatreat.Restaurants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.bookatreat.DataBaseHandler.emailCredentials;


public class CustomerListResFrag extends Fragment {
    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private CustomerExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Restaurants> mExampleList;
    //made this
    private ListView mRestaurantList;

    private static final String KEY_NAME = "Name";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurants = db.collection("restaurants");


    private static final String TAG = "CustomerListResFrag";

    CustomerExampleAdapter adapter;

    private String resName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list_res, container, false);

        //made this
        final ListView mRestaurantList = view.findViewById(R.id.restaurantList);

        // Find views
        final ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);
        final ImageButton mapButton = view.findViewById(R.id.mapsBtn);
        final EditText searchBar = view.findViewById(R.id.searchBar);

        // Initialize ArrayList
        mExampleList = new ArrayList<>();

        // Setup RecyclerView
        mRecyclerView = view.findViewById(R.id.restaurantList);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setUpFireBase();


        // TRYING TO MAKE THE ITEMS CLICKABLE AND TO OPEN A NEW TABLE DIALOG
        //START

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mExampleList);
        mRestaurantList.setAdapter(adapter);

        mRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // openEditTableDialog();
            }
        });









        //END





        // Fill resList
        fillSearchList();

        // Search Bar filtering
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

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

    private void filter(String editText){
        ArrayList<Restaurants> filteredList = new ArrayList<>();

        for(Restaurants item : mExampleList){
            if(item.getName().toLowerCase().contains(editText.toLowerCase())){
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void setUpFireBase(){
        db = FirebaseFirestore.getInstance();
    }

    private void fillSearchList() {
        if(mExampleList.size()>0){
            mExampleList.clear();
        }

        db.collection("restaurants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot querySnapshot: task.getResult()){
                    Restaurants restaurant = new Restaurants(querySnapshot.getString("Address"), querySnapshot.getString("Description"), querySnapshot.getString("Email"), querySnapshot.getString("Name"));
                    mExampleList.add(restaurant);
                }
                mAdapter = new CustomerExampleAdapter(CustomerListResFrag.this, mExampleList);
                mRecyclerView.setAdapter(mAdapter);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Problem ---1---", Toast.LENGTH_SHORT).show();
                        Log.v("---1---", e.getMessage());
                    }
                });
    }

}
