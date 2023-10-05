package com.jcmtechug.stlukeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.jcmtechug.stlukeapp.R;
import com.jcmtechug.stlukeapp.DashboardActivities.Catena;
import com.jcmtechug.stlukeapp.DashboardActivities.Hymns;
import com.jcmtechug.stlukeapp.DashboardActivities.Legion;
import com.jcmtechug.stlukeapp.DashboardActivities.Novena;
import com.jcmtechug.stlukeapp.DashboardActivities.Order;
import com.jcmtechug.stlukeapp.DashboardActivities.Readings;
import com.jcmtechug.stlukeapp.DashboardActivities.Rosary;
import com.jcmtechug.stlukeapp.DashboardActivities.WayOfCross;
import com.jcmtechug.stlukeapp.DashboardActivities.Youtube;


public class Mainmenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
        String christian_name = sharedPreferences.getString("christian_name",null);


        TextView greetings = view.findViewById(R.id.greeting);
        greetings.setText("Hey "+christian_name);



        Navigate(view);

        return view;
    }

    private void Navigate(View view) {
        CardView hymns = view.findViewById(R.id.hymns);
        CardView rosary = view.findViewById(R.id.holyrosary);
        CardView legion = view.findViewById(R.id.legion);
        CardView order = view.findViewById(R.id.ordermass);
        CardView novena = view.findViewById(R.id.novena);
        CardView wayofcross = view.findViewById(R.id.wayofcross);
        CardView catena = view.findViewById(R.id.catena);
        CardView utube = view.findViewById(R.id.utube);
        CardView reading = view.findViewById(R.id.dailyreading);

        wayofcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), WayOfCross.class);
                startActivity(nxt);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Order.class);
                startActivity(nxt);
            }
        });
        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Readings.class);
                startActivity(nxt);
            }
        });

        utube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Youtube.class);
                startActivity(nxt);
            }
        });

        catena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Catena.class);
                startActivity(nxt);
            }
        });

        novena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Novena.class);
                startActivity(nxt);
            }
        });

        hymns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Hymns.class);
                startActivity(nxt);
            }
        });
        legion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Legion.class);
                startActivity(nxt);
            }
        });

        rosary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getActivity(), Rosary.class);
                startActivity(nxt);
            }
        });

    }
}