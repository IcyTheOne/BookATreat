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
import android.widget.ListView;
import android.widget.Switch;
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
    private Switch mTableSwitch;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        dbHandler = new DataBaseHandler();
        bookedTablesList = view.findViewById(R.id.list_view_booked);
        bookedTablesArr = new ArrayList<>();
        mTableSwitch = view.findViewById(R.id.tableSwitch);

        mTableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction cusToRes = getFragmentManager().beginTransaction();
                cusToRes.replace(R.id.container_restaurant, new NewBookingFrag());
                cusToRes.commit();
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

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookedTablesArr);
        bookedTablesList.setAdapter(adapter);

        return view;
    }

    public void openAddNewTableDialog() {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_new_table, null);

        final EditText tableId = mView.findViewById(R.id.tableID);
        final EditText tableSize = mView.findViewById(R.id.tableSize);
        Button add = mView.findViewById(R.id.hourBtn);

        myBuild.setView(mView);
        final AlertDialog dialog = myBuild.create();
        dialog.show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tableIDVal = tableId.getText().toString().trim();
                String tableSizeVal = tableSize.getText().toString().trim();

                if (tableIDVal.equals("0") || tableSizeVal.equals("0")) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                //TODO insert the data to the DB

                boolean success = dbHandler.addTable(tableIDVal, tableSizeVal);

                dialog.dismiss();

                if (success) {
                    Toast.makeText(getContext(), "New table added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error: unable to add the table.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
