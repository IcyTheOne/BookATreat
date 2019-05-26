package com.example.bookatreat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseHandler {

    public static String emailCredentials, passwordCredentials;
    private String UID;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

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
    // private static final String KEY_ID = "UID";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_NAME = "Name";
    private static final String KEY_LAST_NAME = "Last Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_TABLE_ID = "Table number:";
    private static final String KEY_TABLE_SIZE = "Guests:";

    public boolean addTable(String tableId, String size) {
        //TODO: add to database with try catch/ if else

        CollectionReference tablesForOne = restaurants.document(emailCredentials).collection("Tables for 1");
        CollectionReference tablesForTwo = restaurants.document(emailCredentials).collection("Tables for 2");
        CollectionReference tablesForThree = restaurants.document(emailCredentials).collection("Tables for 3");
        CollectionReference tablesForFour = restaurants.document(emailCredentials).collection("Tables for 4");

        Map<String, Object> table = new HashMap<>();
        table.put(KEY_TABLE_ID, tableId);
        table.put(KEY_TABLE_SIZE, size);

        if (size.equals("1")) {
            DocumentReference tableForOne = tablesForOne.document(tableId);
            tableForOne.set(table);
            Log.d(TAG, "Table " + tableId + " added for: " + size + " people");
            return true;
        }

        if (size.equals("2")) {
            DocumentReference tableForTwo = tablesForTwo.document(tableId);
            tableForTwo.set(table);
            Log.d(TAG, "Table " + tableId + " added for: " + size + " people");
            return true;
        }

        if (size.equals("3")) {
            DocumentReference tableForThree = tablesForThree.document(tableId);
            tableForThree.set(table);
            Log.d(TAG, "Table " + tableId + " added for: " + size + " people");
            return true;
        }

        if (size.equals("4")) {
            DocumentReference tableForFour = tablesForFour.document(tableId);
            tableForFour.set(table);
            Log.d(TAG, "Table " + tableId + " added for: " + size + " people");
            return true;
        }
        return false;
    }

    public void emailVerification() {

        if (fUser != null) {
            fUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent to: " + emailCredentials);
                            }
                        }
                    });
        }
    }

    public void saveUser(String firstNameVal, String lastNameVal, String emailVal) {
        UID = fUser.getUid();
        Map<String, Object> user = new HashMap<>();
        user.put(KEY_NAME, firstNameVal);
        user.put(KEY_LAST_NAME, lastNameVal);
        user.put(KEY_EMAIL, emailVal);
        // user.put(KEY_ID, UID);
        user.put(KEY_TYPE, USER_TYPE);

        users.document(UID)
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

    public void saveUser(String resNameVal, String resDescVal, String resEmailVal, String resAddressVal) {
        UID = fUser.getUid();
        Map<String, Object> restaurant = new HashMap<>();
        restaurant.put(KEY_NAME, resNameVal);
        restaurant.put(KEY_DESCRIPTION, resDescVal);
        restaurant.put(KEY_EMAIL, resEmailVal);
        restaurant.put(KEY_ADDRESS, resAddressVal);
        // restaurant.put(KEY_ID, UID);
        restaurant.put(KEY_TYPE, USER_TYPE);

        restaurants.document(UID)
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

    public void reAuthUser() {
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(emailCredentials, passwordCredentials);

        // Prompt the user to re-provide their sign-in credentials
        fUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });
    }

    // Password reset method
    public void resetPassword() {
        mAuth.sendPasswordResetEmail(emailCredentials)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent to: " + emailCredentials);
                        }
                    }
                });
    }

    public void delete() {

        if (USER_TYPE == 1) {
            users.document(UID).delete();
        }

        if (USER_TYPE == 2){
            restaurants.document(UID).delete();
        }
    }
}
