package com.example.bookatreat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignupCustomer extends Fragment{
    private FirebaseAuth mAuth;

    EditText firstname, lastname, password,passwordConfirm, email;
    String firstnameST, lastnameST, passwordST, passwordConfirmST, emailST;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_customer, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Find input views
        firstname = view.findViewById(R.id.firstNameSignup);
        lastname = view.findViewById(R.id.lastNameSignup);
        password = view.findViewById(R.id.passwordSignup);
        passwordConfirm = view.findViewById(R.id.confirmPassSignup);
        email = view.findViewById(R.id.emailSignup);
        // Convert to Strings
        firstnameST = firstname.getText().toString();
        lastnameST = lastname.getText().toString();
        passwordST = password.getText().toString();
        passwordConfirmST = passwordConfirm.getText().toString();
        emailST = email.getText().toString();


        // Click Switch to go to Restaurant Signup
        Switch btnToResSignup = view.findViewById(R.id.switchRestaurant);
        btnToResSignup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction cusToRes = getFragmentManager().beginTransaction();
                cusToRes.replace(R.id.fragment_container, new SignupRestaurant());
                cusToRes.commit();
            }
        });

        // Click text to go to Login page
        TextView btnToLogin = view.findViewById(R.id.goToLoginBtn);
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getActivity(), LoginActivity.class);

                startActivity(secondIntent);
            }
        });

        // Click Signup Button to signup
        TextView btnToSignup = view.findViewById(R.id.signupBtn);
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If any field is empty else if confirm password doesn't match password else if email is already in database else signup and login

                if(firstnameST.isEmpty() || lastnameST.isEmpty() || passwordST.isEmpty() || passwordConfirmST.isEmpty() || emailST.isEmpty()){
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }else if (!passwordST.equals(passwordConfirmST)){
                    Toast.makeText(getContext(), "The passwords doesn't match", Toast.LENGTH_SHORT).show();
                    //} else if (emailST/*is already in database*/){

                } else {
                    // Insert data to database under unique id then login and change to CustomerActivity
                    Intent cusIntent = new Intent(getActivity(), CustomerActivity.class);

                    startActivity(cusIntent);
                }
            }
        });
        return view;
    }
}