package com.example.bookatreat.Customer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.bookatreat.R;

import java.util.ArrayList;


public class CustomerListResFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list_res, container, false);

        // Find views
        final ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);
        final ImageButton mapButton = view.findViewById(R.id.mapsBtn);

//        String[] restaurants = new String[]{
//                "New dehli",
//                "TGI friday",
//                "Gastornomia",
//                "Burger King",
//                "Dominos",
//                "Schipka",
//                "El nacionale",
//        };
//
//
//        List<String> restaurants_list = new ArrayList<>(Arrays.asList(restaurants));
//        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, restaurants_list);
//        restaurantList.setAdapter(arrayAdapter);

        ArrayList<CustomerExampleItem> resList = new ArrayList<>();
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "1 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "2 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "3 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "4 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "5 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "6 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "7 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "8 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "9 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "10 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "11 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "12 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "13 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "14 Le Bahli", "We make good food..."));
        resList.add(new CustomerExampleItem(R.drawable.ic_star_border, "15 Le Bahli", "We make good food..."));

        mRecyclerView = view.findViewById(R.id.restaurantList);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new CustomerExampleAdapter(resList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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
}
