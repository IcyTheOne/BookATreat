package com.example.bookatreat.Customer;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bookatreat.R;

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

        // Setting up Fragment Manager for Customer View
        FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_container_cus);
        fm.beginTransaction().add(R.id.fragment_container_cus, new CustomerListResFrag()).commit();

        // Buttons
        ImageButton restaurantButton = findViewById(R.id.RestaurantBTN);
        ImageButton calendarButton = findViewById(R.id.CalendarBTN);
        ImageButton messageButton = findViewById(R.id.MessagesBTN);
        ImageButton favoritesButton = findViewById(R.id.FavoritesBTN);

        // Go to RestaurantView
        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toCalendar = getSupportFragmentManager().beginTransaction();
                toCalendar.replace(R.id.fragment_container_cus, new CustomerListResFrag());
                toCalendar.commit();
            }
        });

        // Go to Calendar
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toCalendar = getSupportFragmentManager().beginTransaction();
                toCalendar.replace(R.id.fragment_container_cus, new CalendarFrag());
                toCalendar.commit();
            }
        });

        // Go to Messages
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toCalendar = getSupportFragmentManager().beginTransaction();
                toCalendar.replace(R.id.fragment_container_cus, new MessageFrag());
                toCalendar.commit();
            }
        });

        // Go to Favorites
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toCalendar = getSupportFragmentManager().beginTransaction();
                toCalendar.replace(R.id.fragment_container_cus, new FavoritesFrag());
                toCalendar.commit();
            }
        });

    }
}
