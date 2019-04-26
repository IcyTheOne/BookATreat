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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class BookedFrag extends Fragment {
    private FirebaseAuth mAuth;
    private DataBaseHandler dbHandler;
    private ArrayList<String> bookedTablesArr;
    private ListView bookedTablesList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        dbHandler = new DataBaseHandler();
        bookedTablesList = view.findViewById(R.id.list_view_booked);
        bookedTablesArr = new ArrayList<>();

        TextView btnToNewBooking = view.findViewById(R.id.button_new_booking);
        btnToNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction cusToRes = getFragmentManager().beginTransaction();
                cusToRes.replace(R.id.container_restaurant, new NewBookingFrag());
                cusToRes.commit();
            }
        });

        FloatingActionButton btnAddNew = view.findViewById(R.id.floating_add_new);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNewTableDialog();
            }
        });

        //TODO insert the data from DB to an array

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookedTablesArr);
        bookedTablesList.setAdapter(adapter);

        return view;
    }

    public void openAddNewTableDialog() {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_new_table, null);

        final EditText tableId = mView.findViewById(R.id.edit_id);
        final EditText numberOfPeople = mView.findViewById(R.id.edit_number);
        Button add = mView.findViewById(R.id.button_add);

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
