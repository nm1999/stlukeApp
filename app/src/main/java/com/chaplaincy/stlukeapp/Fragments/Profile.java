package com.chaplaincy.stlukeapp.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.Login;
import com.chaplaincy.stlukeapp.R;
import com.chaplaincy.stlukeapp.SignIn;
import com.google.android.material.button.MaterialButton;


public class Profile extends Fragment {

    private MaterialButton logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.logout_btn);


        DBhelper mydbhelper = new DBhelper(getActivity());
        Cursor cr = mydbhelper.getData();

        TextView first = view.findViewById(R.id.firstname);
        TextView sur = view.findViewById(R.id.surname);
        TextView email = view.findViewById(R.id.myemail);
        TextView phone = view.findViewById(R.id.phonenumber);


        while (cr.moveToNext()){
            first.setText(cr.getString(0));
            sur.setText(cr.getString(1));
            email.setText(cr.getString(2));
            phone.setText("+256 "+cr.getString(3));
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
            }
        });
        return view;
    }
}