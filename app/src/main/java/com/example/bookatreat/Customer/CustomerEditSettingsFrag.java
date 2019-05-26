package com.example.bookatreat.Customer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookatreat.R;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerEditSettingsFrag extends Fragment {
    private FirebaseAuth mAuth;

    Button mSaveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_edit_settings, container, false);










    }





}
