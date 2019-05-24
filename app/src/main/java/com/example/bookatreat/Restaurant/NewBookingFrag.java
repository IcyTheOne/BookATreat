package com.example.bookatreat.Restaurant;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class NewBookingFrag extends Fragment {
    private FirebaseAuth mAuth;
    private DataBaseHandler dbHandler;
    private ArrayList<String> arrNew;
    private ListView listNew;
    private ImageButton settingsButton;
    private Switch mTablesSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_bookings, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        dbHandler = new DataBaseHandler();
        arrNew = new ArrayList<>();
        listNew = view.findViewById(R.id.list_view_booked);
        mTablesSwitch = view.findViewById(R.id.tablesSwitch2);

        // Go to Settings
        settingsButton = view.findViewById(R.id.SettingsBTN);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_restaurant, new RestaurantSettingsFrag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });


        mTablesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction cusToRes2 = getFragmentManager().beginTransaction();
                cusToRes2.replace(R.id.container_restaurant, new BookedFrag());
                cusToRes2.commit();
            }
        });

        FloatingActionButton btnAddNew = view.findViewById(R.id.addNewBTN);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNewTableDialog();
            }
        });

        //TODO insert the data from DB to an array

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrNew);
        listNew.setAdapter(adapter);


        return view;
    }

    public void openAddNewTableDialog() {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_new_table, null);

        final EditText tableId = mView.findViewById(R.id.TableID);
        final EditText numberOfPeople = mView.findViewById(R.id.NumberOfGuests);
        Button add = mView.findViewById(R.id.AddTableBTN);

        myBuild.setView(mView);
        final AlertDialog dialog = myBuild.create();
        dialog.show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableId.getText().toString().isEmpty() || numberOfPeople.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                //TODO insert the data to the DB

                boolean success = dbHandler.add(tableId.getText().toString(), numberOfPeople.getText().toString());

                dialog.dismiss();
                if (success)
                    Toast.makeText(getContext(), "New table added", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Error: unable to add the table.", Toast.LENGTH_LONG).show();


            }
        });
    }
}
