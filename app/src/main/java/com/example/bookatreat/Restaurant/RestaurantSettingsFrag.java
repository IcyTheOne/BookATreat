package com.example.bookatreat.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantSettingsFrag extends Fragment {

    DataBaseHandler db = new DataBaseHandler();

    private TextView mNameView;
    private TextView mLastNameView;
    private TextView mEmailView;
    private TextView mDescriptionView;
    private TextView mAddressView;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private String TAG = "User ID is";
    private FirebaseFirestore dab = FirebaseFirestore.getInstance();
    private CollectionReference users = dab.collection("restaurants");
    private DocumentReference mDocumentReference = dab.collection("restaurants").document(uid);




    private String username;

    Button mSignout, mDeleteAcc, mEditBTN;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_settings, container, false);


        mEditBTN = view.findViewById(R.id.editRestBTN);
        mSignout = view.findViewById(R.id.signOutBTN);
        mDeleteAcc = view.findViewById(R.id.deleteAccBTN);

        //

        mNameView = (TextView) view.findViewById(R.id.editName);
        mDescriptionView = (TextView) view.findViewById(R.id.editDescription);
        mAddressView = (TextView) view.findViewById(R.id.editAddress);
        mEmailView = (TextView) view.findViewById(R.id.textViewEmail);
        mDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String Name = document.getString("Name");
                        mNameView.setText(Name);
                        String Description = document.getString("Description");
                        mDescriptionView.setText(Description);
                        String Email = document.getString("Email");
                        mEmailView.setText(Email);
                        String Address = document.getString("Address");
                        mAddressView.setText(Address);

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });










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
                ft.replace(R.id.container_restaurant, new RestaurantEditSettings());
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
        db.reAuthUser();
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
