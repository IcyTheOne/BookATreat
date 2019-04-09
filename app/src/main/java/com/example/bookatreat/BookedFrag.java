package com.example.bookatreat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class BookedFrag extends Fragment {
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        TextView btnToNewBooking = view.findViewById(R.id.button_new_booking);
        btnToNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction cusToRes = getFragmentManager().beginTransaction();
                cusToRes.replace(R.id.container_restaurant, new NewBookingFrag());
                cusToRes.commit();
            }
        });

        return view;
    }
}
