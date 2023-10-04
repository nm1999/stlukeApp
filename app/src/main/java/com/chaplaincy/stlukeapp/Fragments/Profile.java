package com.chaplaincy.stlukeapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.chaplaincy.stlukeapp.R;
import com.google.android.material.button.MaterialButton;
import com.chaplaincy.stlukeapp.SignIn;


public class Profile extends Fragment {

    private MaterialButton logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.logout_btn);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
        String christian_name = sharedPreferences.getString("christian_name",null);
        String othername = sharedPreferences.getString("other_name",null);
        String user_email =  sharedPreferences.getString("email",null);
        String contact = sharedPreferences.getString("contact",null);


        TextView first = view.findViewById(R.id.firstname);
        TextView sur = view.findViewById(R.id.surname);
        TextView email = view.findViewById(R.id.myemail);
        TextView phone = view.findViewById(R.id.phonenumber);


        first.setText(christian_name);
        sur.setText(othername);
        email.setText(user_email);
        phone.setText(contact);


        logout.setOnClickListener(view1 -> {
            // remove all values in sharedpreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
        });
        return view;
    }
}