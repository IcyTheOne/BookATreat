package com.example.bookatreat.Customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.bookatreat.R;

import java.util.ArrayList;

public class MessageFrag extends Fragment {


    private ArrayList<MessageFrag> MessageList;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // Find IDs
        ImageButton settingsButton = view.findViewById(R.id.SettingsBTN);


        ListView messageButton = view.findViewById(R.id.MessagesList);



        MessageList = new ArrayList<>();

        //MessageList.add();









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
