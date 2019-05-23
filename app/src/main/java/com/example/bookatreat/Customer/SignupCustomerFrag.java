package com.example.bookatreat.Customer;

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

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.LoginActivity;
import com.example.bookatreat.R;
import com.example.bookatreat.Restaurant.SignupRestaurantFrag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import static com.example.bookatreat.DataBaseHandler.emailCredentials;
import static com.example.bookatreat.DataBaseHandler.passwordCredentials;

public class SignupCustomerFrag extends Fragment{
    private FirebaseAuth mAuth;
    private DataBaseHandler db = new DataBaseHandler();

    private static final String TAG = "CustomerSignupFrag";

    private TextView mExistingUser, mError;
    private Switch mSignupSwitch;
    private Button mSignupButton;
    private EditText mFirstNameField, mLastNameField, mPasswordField, mPasswordConfirmField, mEmailField;
    String firstNameVal, lastNameVal, passwordVal, passwordConfirmVal, emailVal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_customer, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find input views
        mFirstNameField = view.findViewById(R.id.signupFirstName);
        mLastNameField = view.findViewById(R.id.signupLastName);
        mPasswordField = view.findViewById(R.id.signupPassword);
        mPasswordConfirmField = view.findViewById(R.id.signupPassConfirm);
        mEmailField = view.findViewById(R.id.signupEmail);
        mSignupButton = view.findViewById(R.id.signupBTN);
        mSignupSwitch = view.findViewById(R.id.restaurantSwitch);
        mExistingUser = view.findViewById(R.id.existingUser);
        mError = view.findViewById(R.id.signupError);

        // Switch to restaurant signup
        mSignupSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.setUserType(2);
                System.out.println("USER_TYPE set to: " + db.getUserType());
                FragmentTransaction cusToRes = getFragmentManager().beginTransaction();
                cusToRes.replace(R.id.fragment_container, new SignupRestaurantFrag());
                cusToRes.commit();
            }
        });

        // Click text to go to Login page
        mExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent existingUser = new Intent(getActivity(), LoginActivity.class);
                startActivity(existingUser);
            }
        });

        // Click Signup Button to signup
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameVal = mFirstNameField.getText().toString().trim();
                lastNameVal = mLastNameField.getText().toString().trim();
                passwordVal = mPasswordField.getText().toString().trim();
                passwordConfirmVal = mPasswordConfirmField.getText().toString().trim();
                emailVal = mEmailField.getText().toString().trim();

                if (!passwordConfirmVal.equals(passwordVal)) {
                    Toast.makeText(getActivity(),"Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(emailVal, passwordVal);
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

        emailCredentials = username;
        passwordCredentials = password;

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
                                db.saveUser(firstNameVal, lastNameVal, emailVal);
                                db.emailVerification();
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
                                    mPasswordField.requestFocus();

                                } catch(FirebaseAuthInvalidCredentialsException err) {

                                    mError.setText(R.string.wrong_email_format);
                                    mError.setTextColor(Color.RED);
                                    mEmailField.requestFocus();

                                } catch(FirebaseAuthUserCollisionException err) {

                                    mError.setText(R.string.email_is_in_use);
                                    mError.setTextColor(Color.RED);
                                    mEmailField.requestFocus();

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

        if (mFirstNameField.getText().toString().isEmpty() || mLastNameField.getText().toString().isEmpty() ||
                mEmailField.getText().toString().isEmpty() || mPasswordField.getText().toString().isEmpty() || mPasswordConfirmField.getText().toString().isEmpty()) {

            valid = false;

            if (mFirstNameField.length() == 0) {
                mFirstNameField.requestFocus();
                mFirstNameField.setError("Please enter a name.");
            }

            if (mLastNameField.length() == 0) {
                mLastNameField.requestFocus();
                mLastNameField.setError("Please enter a last name.");
            }

            if (mEmailField.length() == 0) {
                mEmailField.requestFocus();
                mEmailField.setError("Please enter an email.");
            }

            if (mPasswordField.length() == 0) {
                mPasswordField.requestFocus();
                mPasswordField.setError("Please enter a passwordCredentials.");
            }

            if (mPasswordConfirmField.length() == 0) {
                mPasswordConfirmField.requestFocus();
                mPasswordConfirmField.setError("Required.");
            }
        } else { valid = true; }

        if (!passwordConfirmVal.equals(passwordVal)) {
            Toast.makeText(getActivity(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

}