package com.example.bookatreat.LoginSignUp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bookatreat.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        try // Remove ActionBar
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_signup); // End of removing ActionBar

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, new SignupCustomerFrag()).commit();
    }
}
