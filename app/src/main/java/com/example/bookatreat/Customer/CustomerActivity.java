package com.example.bookatreat.Customer;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.example.bookatreat.Restaurants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    DataBaseHandler db = new DataBaseHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);

        // the stuff that should be send to messages
/*
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();
        CustomerListResFrag m11 = new CustomerListResFrag();
        MessageFrag m12 = new MessageFrag();
        t.add(R.id.resNameDialog,m11);
        t.add(R.id.resTimeText,m12);
        t.commit();
*/




        try // Remove ActionBar
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_customer_search); // End of removing ActionBar

        FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_container_cus);
        fm.beginTransaction().add(R.id.fragment_container_cus, new CustomerListResFrag()).commit();


        /**
         * Need to add fragments for all the buttons and their layout
         **/

        ImageButton restaurantButton = findViewById(R.id.RestaurantBTN);
        ImageButton calendarButton = findViewById(R.id.CalendarBTN);
        ImageButton messageButton = findViewById(R.id.MessagesBTN);
        ImageButton favoritesButton = findViewById(R.id.FavoritesBTN);



        // Click listeners
        //Restaurant search

        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent restaurantIntent = new Intent(getBaseContext(), CustomerActivity.class);
                startActivity(restaurantIntent);

            }
        });

        //Calendar bookings
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.i("Info", "Not 1, begin trans");
                    FragmentTransaction toCalendar = getSupportFragmentManager().beginTransaction();
                    toCalendar.replace(R.id.fragment_container_cus, new CalendarFrag()).addToBackStack("tag");
                    toCalendar.commit();
            }
        });

        // Go to Favorites
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentTransaction toFavorites = getSupportFragmentManager().beginTransaction();
                    toFavorites.replace(R.id.fragment_container_cus, new FavoritesFrag()).addToBackStack("tag");
                    toFavorites.commit();

            }
        });

        // Go to Messages
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentTransaction toMessages = getSupportFragmentManager().beginTransaction();
                    toMessages.replace(R.id.fragment_container_cus, new MessageFrag()).addToBackStack("tag");
                    toMessages.commit();
                }
        });

    }
    @Override
    public void onBackPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_cus, new CustomerListResFrag());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        //
    }

}
