package com.example.bookatreat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class NewBookingFrag extends Fragment {
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_bookings, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button btnToReserved = view.findViewById(R.id.button_reserved);
        btnToReserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction cusToRes2 = getFragmentManager().beginTransaction();
                cusToRes2.replace(R.id.container_restaurant, new BookedFrag());
                cusToRes2.commit();
            }
        });

        FloatingActionButton btnAddNew = view.findViewById(R.id.floating_add_new);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction cusToRes2 = getFragmentManager().beginTransaction();
                cusToRes2.replace(R.id.container_restaurant, new AddTableFrag());
                cusToRes2.commit();
            }
        });




        return view;
    }
}
