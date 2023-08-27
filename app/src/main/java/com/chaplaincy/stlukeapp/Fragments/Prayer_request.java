package com.chaplaincy.stlukeapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaplaincy.stlukeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Prayer_request#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Prayer_request extends Fragment {

      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prayer_request, container, false);
    }
}