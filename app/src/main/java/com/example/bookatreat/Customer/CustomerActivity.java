package com.example.bookatreat.Customer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.bookatreat.MapsViewFrag;
import com.example.bookatreat.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);

        try // Remove ActionBar
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_customer_search); // End of removing ActionBar

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_cus);
        fm.beginTransaction().add(R.id.fragment_container_cus, new MapsViewFrag()).commit();


        /**
         * Need to add fragments for all the buttons and their layout
         *
         */

        ImageButton mapButton = findViewById(R.id.mapsbtn);
        ImageButton restaurantButton = findViewById(R.id.restaurantbtn);
        ImageButton calendarButton = findViewById(R.id.calendarbtn);
        ImageButton messageButton = findViewById(R.id.messagesbtn);
        ImageButton settingsButton = findViewById(R.id.favoritesbtn);


        ListView lv = findViewById(R.id.restaurantlist);

        String[] restaurants = new String[]{
          "New dehli",
          "TGI friday",
          "Gastornomia",
          "Burger King",
          "Dominos",
          "Schipka",
          "El nacionale",
                "New dehli",
                "TGI friday",
                "Gastornomia",
                "Burger King",
                "Dominos",
                "Schipka",
                "El nacionale",
                "New dehli",
                "TGI friday",
                "Gastornomia",
                "Burger King",
                "Dominos",
                "Schipka",
                "El nacionale"
        };


        List<String> restaurants_list = new ArrayList<String>(Arrays.asList(restaurants));

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurants_list);
        lv.setAdapter(arrayAdapter);


        // Click listeners



        //google maps

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mapsIntent = new Intent(CustomerActivity.this, CustomerMapsActivity.class);

                startActivity(mapsIntent);


            }
        });

        //restaurant search

        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent restaurantIntent = new Intent(CustomerActivity.this, CustomerActivity.class);
                startActivity(restaurantIntent);

            }
        });

        //Calendar bookings

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //messages

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //settings

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });











    }
}
