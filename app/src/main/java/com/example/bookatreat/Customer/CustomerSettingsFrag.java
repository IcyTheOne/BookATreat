package com.example.bookatreat.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerSettingsFrag extends Fragment {
    private FirebaseAuth mAuth;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DataBaseHandler db = new DataBaseHandler();

    private String username;

    Button mSignout, mDeleteAcc,mEditBTN;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_settings, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mSignout = view.findViewById(R.id.signOutBTN);
        mDeleteAcc = view.findViewById(R.id.deleteAccBTN);
        mEditBTN = view.findViewById(R.id.editBTN);

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        mDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAcc();
            }
        });

        mEditBTN.setOnClickListener();



        return view;
    }

    public void signOut() {

        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
    }

    public void deleteAcc() {
        db.delete();

        AuthUI.getInstance()
                .delete(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now deleted
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });
    }

}
