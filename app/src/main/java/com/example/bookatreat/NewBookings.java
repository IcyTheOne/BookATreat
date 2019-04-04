package com.example.bookatreat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;


public class NewBookings extends Fragment {
    private FirebaseAuth mAuth;

    private EditText firstname, lastname, password, passwordConfirm, email;
    String firstnameST, lastnameST, passwordST, passwordConfirmST, emailST;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_customer, container, false);

        return view;
    }
}