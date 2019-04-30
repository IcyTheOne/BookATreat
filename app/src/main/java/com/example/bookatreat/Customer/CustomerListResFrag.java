package com.example.bookatreat.Customer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.bookatreat.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CustomerListResFrag extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list_res, container, false);

        final ImageButton mapButton = view.findViewById(R.id.MapsBTN);
        ListView restaurantList = view.findViewById(R.id.RestaurantList);

        String[] restaurants = new String[]{
                "New dehli",
                "TGI friday",
                "Gastornomia",
                "Burger King",
                "Dominos",
                "Schipka",
                "El nacionale",
        };


        List<String> restaurants_list = new ArrayList<>(Arrays.asList(restaurants));

        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, restaurants_list);
        restaurantList.setAdapter(arrayAdapter);

        // Go to Maps View
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toMapsView = getFragmentManager().beginTransaction();
                toMapsView.replace(R.id.fragment_container_cus, new MapsViewFrag());
                toMapsView.commit();
            }
        });

    return view;
    }
}
