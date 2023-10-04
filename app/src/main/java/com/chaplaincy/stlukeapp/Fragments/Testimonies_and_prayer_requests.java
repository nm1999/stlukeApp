package com.chaplaincy.stlukeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.chaplaincy.stlukeapp.R;


public class Testimonies_and_prayer_requests extends Fragment {
    private TextView testimonies,prayer_requests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testimonies_and_prayer_requests, container, false);
        testimonies = view.findViewById(R.id.testimonies);
        prayer_requests = view.findViewById(R.id.prayer_requests);

        handleClickEvents(view);
        return view;
    }

    private void handleClickEvents(View view) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Testimony_view()).commit();

        testimonies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Testimony_view()).commit();
            }
        });

        prayer_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Prayer_request()).commit();
            }
        });
    }
}