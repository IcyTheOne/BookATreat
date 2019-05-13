package com.example.bookatreat.Restaurant;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.Customer.CustomerActivity;
import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.example.bookatreat.Customer.SignupCustomerFrag;
import com.example.bookatreat.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import static com.example.bookatreat.UserType.USER_TYPE;

public class SignupRestaurantFrag extends Fragment {
    private FirebaseAuth mAuth;

    UserType type = new UserType();

    private String TAG = "RestaurantSignupActivity";

    private TextView mExistingUser, mError;
    private Switch mSignupSwitch;
    private Button mSignupButton;
    private EditText mResNameField, mResDescField, mResPassField, mResPassConfirmField, mResEmailField, mResAddressField;
    String resNameVal, resDescVal, resPassVal, resPassConfirmVal, resEmailVal, resAddressVal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_restaurant, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find input views
        mResNameField = view.findViewById(R.id.signupRName);
        mResDescField = view.findViewById(R.id.signupRDescription);
        mResPassField = view.findViewById(R.id.signupPassword);
        mResPassConfirmField = view.findViewById(R.id.signupPassConfirm);
        mResEmailField = view.findViewById(R.id.signupEmail);
        mResAddressField = view.findViewById(R.id.signupRAddress);
        mSignupButton = view.findViewById(R.id.signupBTN);
        mSignupSwitch = view.findViewById(R.id.restaurantSwitch);
        mExistingUser = view.findViewById(R.id.existingUser);
        mError = view.findViewById(R.id.signupError);

        // Switch to customer signup
        mSignupSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        mExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(secondIntent);
            }
        });

        // Click Signup Button to signup
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resNameVal = mResNameField.getText().toString();
                resDescVal = mResDescField.getText().toString();
                resPassVal = mResPassField.getText().toString();
                resPassConfirmVal = mResPassConfirmField.getText().toString();
                resEmailVal = mResEmailField.getText().toString();
                resAddressVal = mResAddressField.getText().toString();

                if (!resPassConfirmVal.equals(resPassVal)) {
                    Toast.makeText(getActivity(),"Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(resEmailVal, resPassVal);
                }
            }
        });

        return view;
    }

    public void createAccount(String username, String password) {

        Log.d(TAG, "createAccount:" + username);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {
                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(getActivity(), "registration successful", Toast.LENGTH_SHORT).show();
                                //finish();
                                startActivity(new Intent(getActivity(), CustomerActivity.class));
                            } else {
                                Toast.makeText(getActivity(), "Couldn't register, try again", Toast.LENGTH_SHORT).show();
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Log.e("SignupActivity", "Failed Registration", e);
                                //TODO: Add text on screen with error

                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthWeakPasswordException err) {

                                    mError.setText(R.string.weak_password_exception);
                                    mError.setTextColor(Color.RED);
                                    mResPassField.requestFocus();

                                } catch(FirebaseAuthInvalidCredentialsException err) {

                                    mError.setText(R.string.wrong_email_format);
                                    mError.setTextColor(Color.RED);
                                    mResEmailField.requestFocus();

                                } catch(FirebaseAuthUserCollisionException err) {

                                    mError.setText(R.string.email_is_in_use);
                                    mError.setTextColor(Color.RED);
                                    mResEmailField.requestFocus();

                                } catch(Exception err) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private boolean validateForm() {

        boolean valid;

        if (mResNameField.getText().toString().isEmpty() || mResDescField.getText().toString().isEmpty() ||
                mResAddressField.getText().toString().isEmpty() || mResEmailField.getText().toString().isEmpty() ||
                mResPassField.getText().toString().isEmpty() || mResPassConfirmField.getText().toString().isEmpty()) {

            valid = false;

            if (mResNameField.length() == 0) {
                mResNameField.requestFocus();
                mResNameField.setError("Please enter a name.");
            }

            if (mResDescField.length() == 0) {
                mResDescField.requestFocus();
                mResDescField.setError("Please enter a description.");
            }

            if (mResAddressField.length() == 0) {
                mResAddressField.requestFocus();
                mResAddressField.setError("Please enter an address.");
            }

            if (mResEmailField.length() == 0) {
                mResEmailField.requestFocus();
                mResEmailField.setError("Please enter an email.");
            }

            if (mResPassField.length() == 0) {
                mResPassField.requestFocus();
                mResPassField.setError("Required.");
            }

            if (mResPassConfirmField.length() == 0) {
                mResPassConfirmField.requestFocus();
                mResPassConfirmField.setError("Required.");
            }
        } else { valid = true; }

        if (!resPassConfirmVal.equals(resPassVal)) {
            Toast.makeText(getActivity(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
}

