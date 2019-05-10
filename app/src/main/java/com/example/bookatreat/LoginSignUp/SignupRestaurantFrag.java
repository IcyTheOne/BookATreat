package com.example.bookatreat.Restaurant;

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

import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.example.bookatreat.Customer.SignupCustomerFrag;
import com.example.bookatreat.UserType;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.bookatreat.UserType.USER_TYPE;

public class SignupRestaurantFrag extends Fragment {
    private FirebaseAuth mAuth;

    UserType type = new UserType();

    EditText resName, resDsc, resPass, resPassConfirm, resEmail, resAddress;
    String resNameST, resDscST, resPassST, resPassConfirmST, resEmailST, resAddressST;

    // TEST BUTTON FOR COMMITING


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_restaurant, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find input views
        resName = view.findViewById(R.id.signupRName);
        resDsc = view.findViewById(R.id.signupRDescription);
        resPass = view.findViewById(R.id.signupPassword);
        resPassConfirm = view.findViewById(R.id.signupPassConfirm);
        resEmail = view.findViewById(R.id.signupEmail);
        resAddress = view.findViewById(R.id.signupRAddress);

        // Switch to Customer signup page
        Switch btnToCusSignup = view.findViewById(R.id.restaurantSwitch);
        btnToCusSignup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isNotChecked) {
                USER_TYPE = 1;
                System.out.println("USER_TYPE set to: " + type.getUserType());
                FragmentTransaction resToCus = getFragmentManager().beginTransaction();
                resToCus.replace(R.id.fragment_container, new SignupCustomerFrag());
                resToCus.commit();
            }
        });

        // Go to login page
        TextView btnToLogin = view.findViewById(R.id.existingUser);
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getActivity(), LoginActivity.class);

                startActivity(secondIntent);
            }
        });

        // Click Signup Button to signup
        TextView btnToSignup = view.findViewById(R.id.signupBTN);
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resNameST = resName.getText().toString();
                resDscST = resDsc.getText().toString();
                resPassST = resPass.getText().toString();
                resPassConfirmST = resPassConfirm.getText().toString();
                resEmailST = resEmail.getText().toString();
                resAddressST = resAddress.getText().toString();

                // If any field is empty else if confirm password doesn't match password else if email is already in database else signup and login

                if(resNameST.isEmpty() || resDscST.isEmpty() || resPassST.isEmpty() || resPassConfirmST.isEmpty() || resEmailST.isEmpty() || resAddressST.isEmpty()){
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }else if (!resPassST.equals(resPassConfirmST)){
                    Toast.makeText(getContext(), "The passwords doesn't match", Toast.LENGTH_SHORT).show();
                    //} else if (emailST/*is already in database or address is already used*/){

                } else {
                    // Insert data to database under unique id then login and change to CustomerActivity
                    Intent secondIntent = new Intent(getActivity(), RestaurantActivity.class);

                    startActivity(secondIntent);
                }
            }
        });

        return view;
    }
}

