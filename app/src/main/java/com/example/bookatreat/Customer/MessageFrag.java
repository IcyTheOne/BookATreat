package com.example.bookatreat.Customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookatreat.R;

import java.util.ArrayList;

public class MessageFrag extends Fragment {


    private ArrayList<String> MessageArr;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // Find IDs
        ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);

        ListView messagesList = view.findViewById(R.id.MessagesList);



        MessageArr = new ArrayList<>();

        MessageArr.add("TGI FRIDAY, CONFIRMATION");


        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, MessageArr);
        messagesList.setAdapter(adapter);


 /*       public void onItemClick(AdapterView <? > arg0, View arg1, int arg2,
        long arg3) {
            Log.v("clicked", (String)((TextView) arg1).getText());
        }

    });*/



        // Go to Settings
        settingsButton.setOnClickListener(new View. OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_cus, new CustomerSettingsFrag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return view;
    }
}
