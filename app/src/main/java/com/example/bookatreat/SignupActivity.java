package com.example.bookatreat;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bookatreat.Customer.SignupCustomerFrag;
import com.example.bookatreat.Restaurant.SignupRestaurantFrag;

public class SignupActivity extends AppCompatActivity {

    UserType type = new UserType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        try // Remove ActionBar
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_signup); // End of removing ActionBar

        if (type.getUserType() == 1) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, new SignupCustomerFrag()).commit();
        }

        if (type.getUserType() == 2) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, new SignupRestaurantFrag()).commit();
        }

    }

}
