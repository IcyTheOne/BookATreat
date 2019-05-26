package com.example.bookatreat.Restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookatreat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class restaurant_edit_settings extends Fragment {
    private EditText mNameEdit;
    private EditText mAddress;
    private EditText mEmailEdit;
    private EditText mDescription;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String TAG = "User ID is";
    private FirebaseFirestore dab = FirebaseFirestore.getInstance();
    private DocumentReference mDocumentReference = dab.collection("restaurants").document(uid);
    private static final String KEY_NAME = "Name";
    private static final String KEY_ADDRESS = "Last Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_DESCRIPTION ="Description";
    Button mSaveButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_edit_settings, container, false);
        //Path to the EditText

        mNameEdit = view.findViewById(R.id.editName);
        mAddress = view.findViewById(R.id.editAddress);
        mEmailEdit = view.findViewById(R.id.editEmail);
        mDescription = view.findViewById(R.id.editDescription);
        mSaveButton = view.findViewById(R.id.editRestBTN);


        //storing the values of the EditText


        String newName = mNameEdit.getText().toString().trim();
        String newDescription = mDescription.getText().toString().trim();
        String newEmail = mEmailEdit.getText().toString().trim();
        String newAddress = mAddress.getText().toString().trim();


        //Database HashMap

        final Map<String, Object> path = new HashMap<>();
        path.put(KEY_NAME, newName);
        path.put(KEY_ADDRESS, newAddress);
        path.put(KEY_EMAIL, newEmail);
        path.put(KEY_DESCRIPTION, newDescription);
        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String newName = mNameEdit.getText().toString().trim();
                String newDescription = mDescription.getText().toString().trim();
                String newEmail = mEmailEdit.getText().toString().trim();
                String newAddress = mAddress.getText().toString().trim();

                final Map<String, Object> path = new HashMap<>();
                path.put(KEY_NAME, newName);
                path.put(KEY_ADDRESS, newAddress);
                path.put(KEY_EMAIL, newEmail);
                path.put(KEY_DESCRIPTION, newDescription);

                Log.d(TAG, "DocumentSnapshot successfully written!");
                dab.collection("restaurants").document(uid).set(path)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

            }


        });










        return view;
    }}



