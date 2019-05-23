package com.example.bookatreat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.Customer.CustomerActivity;
import com.example.bookatreat.Restaurant.RestaurantActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.bookatreat.DataBaseHandler.USER_TYPE;
import static com.example.bookatreat.DataBaseHandler.emailCredentials;
import static com.example.bookatreat.DataBaseHandler.passwordCredentials;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserNameField, mPasswordField;
    private TextView mNewUserText;
    private Button mLoginButton;
    private Switch mLoginSwitch;

    private String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    DataBaseHandler db = new DataBaseHandler();

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.setUserType(1);
        Log.d(TAG, "USER_TYPE set to: " + USER_TYPE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db.setUserType(1);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        try // Remove ActionBar
        {
            this.getSupportActionBar().hide();
        }

        catch (NullPointerException e){}

        setContentView(R.layout.activity_login); // End of removing ActionBar

        // Find input views
        mLoginSwitch = findViewById(R.id.loginSwitchBTN);

        mUserNameField = findViewById(R.id.loginUsername);
        emailCredentials = mUserNameField.getText().toString().trim();
        mPasswordField = findViewById(R.id.loginPassword);

        mLoginButton = findViewById(R.id.loginBTN);
        mNewUserText = findViewById(R.id.signupHyperlink);

        // Click text to go to sign up page
        mNewUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

            }
        });

        // Click switch to either sign in as restaurant or user
        //TODO: above^
        mLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                loginSwitch(db.getUserType());

            }
        });

        // Click button to sign in to the app
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUserNameField.getText().toString().trim();
                String passwordVal = mPasswordField.getText().toString().trim();

                if (username.isEmpty() || passwordVal.isEmpty()) {

                    if (username.length() == 0) {
                        mUserNameField.requestFocus();
                        mUserNameField.setError("Please enter a valid email.");
                    }

                    if (passwordVal.length() == 0) {
                        mPasswordField.requestFocus();
                        mPasswordField.setError("Please enter a passwordCredentials.");
                    }
                } else {
                    emailCredentials = username;
                    passwordCredentials = passwordVal;
                    Log.d(TAG, "Username credentials set to: " + username);
                    Log.d(TAG, "Password credentials set to: " + passwordVal);
                    signIn(username, passwordVal);
                }
            }
        });
    }

    private void loginSwitch(int i) {

        if (i == 1) {
            db.setUserType(2);
            System.out.println("USER_TYPE set to: " + db.getUserType());
        }

        if (i == 2) {
            db.setUserType(1);
            System.out.println("USER_TYPE set to: " + db.getUserType());
        }
    }

    private void signIn(String username, String password) {

        Log.d(TAG, "signIn:" + username);

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();
                            finish();

                            if (db.getUserType() == 1) {
                                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), RestaurantActivity.class));
                            }


                        } else {

                            System.out.println("Sign-in Failed: " + task.getException().getMessage());

                            Toast.makeText(LoginActivity.this, "couldn't login", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void forgotPassword() {
        mAuth.sendPasswordResetEmail(emailCredentials)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent to: " + emailCredentials);
                            Toast.makeText(LoginActivity.this, "Email sent.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

