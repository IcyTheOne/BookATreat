package com.example.bookatreat.Customer;


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


import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CustomerEditSettingsFrag extends Fragment  {

    private EditText mNameEdit;
    private EditText mLastNameEdit;
    private EditText mEmailEdit;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String TAG = "User ID is";
    private FirebaseFirestore dab = FirebaseFirestore.getInstance();
    private DocumentReference mDocumentReference = dab.collection("users").document(uid);
    private static final String KEY_NAME = "Name";
    private static final String KEY_LAST_NAME = "Last Name";
    private static final String KEY_EMAIL = "Email";

    Button mSaveButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_edit_settings, container, false);

        //Path to the EditText

        mNameEdit = view.findViewById(R.id.editName);
        mLastNameEdit = view.findViewById(R.id.editLastName);
        mEmailEdit = view.findViewById(R.id.editEmail);


        //storing the values of the EditText


        String newName = mNameEdit.getText().toString().trim();
        String newLastName = mLastNameEdit.getText().toString().trim();
        String newEmail = mEmailEdit.getText().toString().trim();


        mSaveButton = view.findViewById(R.id.saveBTN);

        //Database HashMap

        final Map<String, Object> path = new HashMap<>();
        path.put(KEY_NAME, newName);
        path.put(KEY_LAST_NAME, newLastName);
        path.put(KEY_EMAIL, newEmail);




        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
                dab.collection("users").document(uid).set(path)
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


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_edit_settings, container, false);


    }
}
