package com.example.bookatreat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataBaseHandler {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

    private String emailAddress;

    // User type
    public static int USER_TYPE;
    public int getUserType() {
        return USER_TYPE;
    }
    public void setUserType(int i) { USER_TYPE = i; }

    // Collection references
    private CollectionReference users = db.collection("users");
    private CollectionReference restaurants = db.collection("restaurants");

    private static final String TAG = "CustomerSignupFrag";
    private static final String KEY_NAME = "Name";
    private static final String KEY_LNAME = "Last Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_DESCRIPTION = "Description";

    public boolean add(String tableId, String numOfPeople) {
        //TODO: add to database with try catch/ if else
        return true;
    }

    public void emailVerification() {

        if (fUser != null) {
            fUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent to: " + fUser.getEmail());
                            }
                        }
                    });
        }
    }

    public void saveUser(String firstNameVal, String lastNameVal, String emailVal, String passwordVal) {
        Map<String, Object> user = new HashMap<>();
        user.put(KEY_NAME, firstNameVal);
        user.put(KEY_LNAME, lastNameVal);
        user.put(KEY_EMAIL, emailVal);
        user.put(KEY_PASSWORD, passwordVal);

        users.document(emailVal)
                .set(user)
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

    public void saveUser(String resNameVal, String resDescVal, String resEmailVal, String resPassVal, String resAddressVal) {
        Map<String, Object> restaurant = new HashMap<>();
        restaurant.put(KEY_NAME, resNameVal);
        restaurant.put(KEY_DESCRIPTION, resDescVal);
        restaurant.put(KEY_EMAIL, resEmailVal);
        restaurant.put(KEY_PASSWORD, resPassVal);
        restaurant.put(KEY_ADDRESS, resAddressVal);

        restaurants.document(resEmailVal)
                .set(restaurant)
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

    // Password reset method
    public void resetPassword(String emailVal) {
        mAuth.sendPasswordResetEmail(emailVal)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    private void getUserEmail() {
        if (fUser != null) {
            emailAddress = fUser.getEmail();
        }
    }

    public void delete() {

        getUserEmail();

        if (USER_TYPE == 1) {
            users.document(emailAddress).delete();
        } else {
            restaurants.document(emailAddress).delete();
        }
    }

}
