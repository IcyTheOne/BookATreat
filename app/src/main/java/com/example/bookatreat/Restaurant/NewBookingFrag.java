package com.example.bookatreat.Restaurant;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bookatreat.DataBaseHandler;
import com.example.bookatreat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

public class NewBookingFrag extends Fragment {
    private FirebaseAuth mAuth;
    private DataBaseHandler dbHandler;
    private ArrayList<String> arrNew;
    private ListView listNew;
    private ImageButton settingsButton;
    private Switch mTablesSwitch;

    private TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_bookings, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        dbHandler = new DataBaseHandler();
        arrNew = new ArrayList<>();
        listNew = view.findViewById(R.id.list_view_booked);
        mTablesSwitch = view.findViewById(R.id.tableSwitch);

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

        arrNew.add("Table 1, 3 people");
        arrNew.add("Table 4, 5 people");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrNew);
        listNew.setAdapter(adapter);

        listNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEditTableDialog();
            }
        });

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
                if (success)
                    Toast.makeText(getContext(), "New table added", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Error: unable to add the table.", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void openEditTableDialog() {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_edit_table, null);

        final EditText tableId = mView.findViewById(R.id.tableID);
        final EditText tableSize = mView.findViewById(R.id.tableSize);
        Button book = mView.findViewById(R.id.bookTableBtn);

        final TextView hour = mView.findViewById(R.id.hourTextView);
        Button time = mView.findViewById(R.id.hourBtn);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hour.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        myBuild.setView(mView);
        final AlertDialog dialog = myBuild.create();
        dialog.show();

        book.setOnClickListener(new View.OnClickListener() {
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
