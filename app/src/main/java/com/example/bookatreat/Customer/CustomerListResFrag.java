package com.example.bookatreat.Customer;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;


public class CustomerListResFrag extends Fragment implements CustomerExampleAdapter.OnResClickListener {
    private RecyclerView mRecyclerView;
    private CustomerExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Restaurants> mExampleList;
    public static ArrayList<Restaurants> mFavArrList;

    // the timepicker fields
    private TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm, UID;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurants = db.collection("restaurants");

    private static final String TAG = "CustomerListResFrag";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list_res, container, false);

        // Find views
        final ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);
        final ImageButton mapButton = view.findViewById(R.id.mapsBtn);
        final EditText searchBar = view.findViewById(R.id.searchBar);

        // Initialize ArrayList
        mExampleList = new ArrayList<>();
        mFavArrList = new ArrayList<>();

        // Setup RecyclerView
        mRecyclerView = view.findViewById(R.id.restaurantList);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setUpFireBase();

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
                //ft.replace(R.id.fragment_container_cus, new MapsViewFrag());
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
        mAdapter = new CustomerExampleAdapter(CustomerListResFrag.this, mExampleList, this);
        db.collection("restaurants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot querySnapshot: task.getResult()){
                    Restaurants restaurant = new Restaurants(querySnapshot.getString("Address"), querySnapshot.getString("Description"), querySnapshot.getString("Email"), querySnapshot.getString("Name"));
                    mExampleList.add(restaurant);
                }
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

    public static ArrayList<Restaurants> getFavArrList() {
        return mFavArrList;
    }

    // The open Restaurants dialog method
    public void OpenRestaurantDialog(final int position) {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_res_view, null);

        final TextView resNameD = mView.findViewById(R.id.resNameDialog);
        final TextView resDesD = mView.findViewById(R.id.resDesDialog);
        final TextView resEmailD = mView.findViewById(R.id.resEmailDialog);
        final TextView resAddD = mView.findViewById(R.id.resAddressDialog);
        final ImageView favStar = mView.findViewById(R.id.favStar);

        final TextView resPersonsTxt = mView.findViewById(R.id.resPersonsText);
        final TextView resPersonsEdi = mView.findViewById(R.id.resPersonsEdit);
        final TextView resTimeTxt = mView.findViewById(R.id.resTimeText);
        final TextView resTimeBtn = mView.findViewById(R.id.resTimeButton);
        final TextView resCloseDialogBtn = mView.findViewById(R.id.resCloseDialogButton);
        final TextView resBookBtn = mView.findViewById(R.id.resBookButton);


        resTimeTxt.setText("00:00");
        resPersonsTxt.setText("Persons:");
        resPersonsEdi.setText("0");
        resNameD.setText(mExampleList.get(position).getName());
        resDesD.setText(mExampleList.get(position).getDescription());
        resAddD.setText(mExampleList.get(position).getAddress());
        resEmailD.setText(mExampleList.get(position).getEmail());

        //On Click of book button

        resBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            // create new document with resEmailD
                //create theese fields
                // resTimeTxt
                //






            }
        });

        //On Click of Star Symbol
        favStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add position to FavoritesArray
                Restaurants favRes = mExampleList.get(position);
                mFavArrList.add(favRes);
            }
        });

        myBuild.setView(mView);
        final AlertDialog dialog = myBuild.create();
        dialog.show();


        //On Click Close dialog button

         resCloseDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });


        resTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);


                // the timepicker dialog
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        resTimeTxt.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });


    }

    @Override
    public void onResClick(int position) {
        Log.d(TAG, "onResClick: Clicked Pos - " + position);

        OpenRestaurantDialog(position);
    }
}
