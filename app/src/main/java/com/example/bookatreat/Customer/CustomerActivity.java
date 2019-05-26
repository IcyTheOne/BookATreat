package com.example.bookatreat.Customer;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    DataBaseHandler db = new DataBaseHandler();

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
                FragmentTransaction toCalendar = getSupportFragmentManager().beginTransaction();
                toCalendar.replace(R.id.fragment_container_cus, new CalendarFrag());
                toCalendar.commit();
            }
        });

        // Go to Favorites
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toFavorites = getSupportFragmentManager().beginTransaction();
                toFavorites.replace(R.id.fragment_container_cus, new FavoritesFrag());
                toFavorites.commit();
            }
        });

        // Go to Messages
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction toMessages = getSupportFragmentManager().beginTransaction();
                toMessages.replace(R.id.fragment_container_cus, new MessageFrag());
                toMessages.commit();
            }
        });

    }
}
