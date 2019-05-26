package com.example.bookatreat.Customer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookatreat.R;

import java.util.ArrayList;

public class MessageFrag extends Fragment {

    private ArrayList<String> MessageArr;
    private ListView messagesList;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        MessageArr = new ArrayList<>();



        // Find IDs
        ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);
        messagesList = view.findViewById(R.id.MessagesList);

        //Items on list (delete later)
        MessageArr.add("TGI FRIDAY, CONFIRMATION");
        MessageArr.add("EL NACIONALE, CONFIRMATION");
        MessageArr.add("BURGER KING, CONFIRMATION");
        MessageArr.add("MC DONALDS, CONFIRMATION");



        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, MessageArr);
        messagesList.setAdapter(adapter);


        // Go to Settings
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_cus, new CustomerSettingsFrag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });



        // The on item click listener
        messagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenMessageDialog();
            }
        });
        return view;

    }


    // The open message dialog method
    public void OpenMessageDialog() {
        final AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_enlarge_message_table, null);

         TextView restaurantName = mView.findViewById(R.id.RestaurantName);
         TextView messageHeader = mView.findViewById(R.id.RestaurantMessageHeader);
         TextView fullMessage = mView.findViewById(R.id.RestaurantMessage);
         Button closeMessageBtn  = mView.findViewById(R.id.CloseMessageBtn);

        myBuild.setView(mView);
        final AlertDialog dialog = myBuild.create();
        dialog.show();

        // the textviews

        //resNameD.setText(mExampleList.get(position).getName());

        restaurantName.setText("TGI FRIDAY");
        messageHeader.setText("CONFIRMATION");
        fullMessage.setText("This is where the message appears");


  /*      restaurantName.setText(MessageFrag.get(position).getName());
        messageHeader.setText(mExampleList.get(position).getDescription());
        fullMessage.setText(mExampleList.get(position).getAddress());*/


        closeMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    } //end of opendialog method



}




