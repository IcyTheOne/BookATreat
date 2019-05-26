package com.example.bookatreat.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.example.bookatreat.Restaurants;
import com.example.bookatreat.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

import static com.example.bookatreat.DataBaseHandler.USER_TYPE;


public class CustomerSettingsFrag extends Fragment {
    private FirebaseAuth mAuth;


    DataBaseHandler db = new DataBaseHandler();
    private DatabaseReference mDatabase;
    private TextView mNameView;
    private TextView mLastNameView;
    private TextView mEmailView;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private String TAG = "User ID is";
    private FirebaseFirestore dab = FirebaseFirestore.getInstance();
    private CollectionReference users = dab.collection("users");
    private DocumentReference mDocumentReference = dab.collection("users").document(uid);



    CustomerExampleAdapter mAdapter;


    Button mSignout, mDeleteAcc,mEditBTN;




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_settings, container, false);

        Log.d(TAG, "USER ID IS                                                                " + uid);







        //Populate textViews of customerSettings with their information
        mAuth = FirebaseAuth.getInstance();

        mNameView = (TextView) view.findViewById(R.id.textViewName);
        mLastNameView = (TextView) view.findViewById(R.id.textViewLastName);
        mEmailView = (TextView) view.findViewById(R.id.textViewEmail);

        mDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String Name = document.getString("Name");
                        mNameView.setText(Name);
                        String LastName = document.getString("Last Name");
                        mLastNameView.setText(LastName);
                        String Email = document.getString("Email");
                        mEmailView.setText(Email);

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });





















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

        mEditBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_cus, new CustomerEditSettingsFrag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });








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
