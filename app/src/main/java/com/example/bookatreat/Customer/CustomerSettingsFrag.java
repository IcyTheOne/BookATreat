package com.example.bookatreat.Customer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerSettingsFrag extends Fragment {
    private FirebaseAuth mAuth;

    DataBaseHandler dbHandler = new DataBaseHandler();
    private TextView mNameView;
    private TextView mLastNameView;
    private TextView mEmailView;

    private EditText mNameEdit;
    private EditText mLastNameEdit;
    private EditText mEmailEdit;

    private boolean overTenPer;

    private static final String TAG = "CustomerSettingsFrag";

    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference users = db.collection("users");

    private String UID = FirebaseAuth.getInstance().getUid();
    DocumentReference mDocumentReference = users.document(UID);

    CustomerExampleAdapter mAdapter;

    Button mSignout, mDeleteAcc, mEditBTN, mSaveButton;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            if (level < 10) {
                overTenPer = false;
            } else {
                overTenPer = true;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_settings, container, false);
        View mview = inflater.inflate(R.layout.fragment_customer_edit_settings,container, false);

        //Populate textViews of customerSettings with their information
        mAuth = FirebaseAuth.getInstance();

        mNameView = (TextView) view.findViewById(R.id.editName);
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

        //Link the buttons
        mSignout = view.findViewById(R.id.signOutBTN);
        mDeleteAcc = view.findViewById(R.id.deleteAccBTN);
        mEditBTN = view.findViewById(R.id.editBTN);
        mSaveButton = mview.findViewById(R.id.saveBTN);

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

        //Battery mode

        getContext().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        mEditBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (overTenPer) {

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container_cus, new CustomerEditSettingsFrag());
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "Your battery is too low to perform this action", Toast.LENGTH_LONG).show();
                    }

                }
            });



        mNameEdit = mview.findViewById(R.id.editName);
        mLastNameEdit = mview.findViewById(R.id.editLastName);
        mEmailEdit = mview.findViewById(R.id.editEmail);

        mSaveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //Add Rewrite data method

                mDocumentReference.set(UID)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            String Name = mNameEdit.toString();

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



        }

        );

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
        dbHandler.delete();

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
