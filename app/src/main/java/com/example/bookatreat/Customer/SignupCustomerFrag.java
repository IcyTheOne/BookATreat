package com.example.bookatreat.Customer;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.example.bookatreat.Restaurant.SignupRestaurantFrag;
import com.example.bookatreat.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class SignupCustomerFrag extends Fragment{
    private FirebaseAuth mAuth;

    private EditText mFirstName, mLastName, mPassword, mPasswordConfirm, mEmail;
    String firstNameValue, lastNameValue, passwordValue, confirmPassValue, emailValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_customer, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find input views
        mFirstName = view.findViewById(R.id.signupFirstName);
        mLastName = view.findViewById(R.id.signupLastName);
        mPassword = view.findViewById(R.id.signupRPassword);
        mPasswordConfirm = view.findViewById(R.id.signupRPassConfirm);
        mEmail = view.findViewById(R.id.signupREmail);

        // Click Switch to go to Restaurant Signup
        Switch restaurantSwitch = view.findViewById(R.id.restaurantSwitch);
        restaurantSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction cusToRes = getFragmentManager().beginTransaction();
                cusToRes.replace(R.id.fragment_container, new SignupRestaurantFrag());
                cusToRes.commit();
            }
        });

        // Click text to go to Login page
        TextView existingUser = view.findViewById(R.id.existingUser);
        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getActivity(), LoginActivity.class);

                startActivity(secondIntent);
            }
        });

        // Click Signup Button to signup
        TextView btnToSignup = view.findViewById(R.id.SignupRBTN);
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameValue = mFirstName.getText().toString();
                lastNameValue = mLastName.getText().toString();
                passwordValue = mPassword.getText().toString();
                confirmPassValue = mPasswordConfirm.getText().toString();
                emailValue = mEmail.getText().toString();

                // If any field is empty else if confirm mPassword doesn't match mPassword else if mEmail is already in database else signup and login

                if(firstNameValue.isEmpty() || lastNameValue.isEmpty() || passwordValue.isEmpty() || confirmPassValue.isEmpty() || emailValue.isEmpty()){
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!passwordValue.equals(confirmPassValue)){
                    Toast.makeText(getContext(), "The passwords doesn't match", Toast.LENGTH_SHORT).show();
                    //} else if (emailValue/*is already in database*/){

                } else {
                    // Insert data to database under unique id then login and change to CustomerActivity
                    Intent cusIntent = new Intent(getActivity(), CustomerActivity.class);

                    startActivity(cusIntent);
                }
            }
        });
        return view;
    }

    //TODO: Fix this methot

    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}