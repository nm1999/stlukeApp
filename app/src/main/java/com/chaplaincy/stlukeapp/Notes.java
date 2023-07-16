package com.chaplaincy.stlukeapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Notes extends Fragment {
    private FloatingActionButton floatbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        DBhelper mydbhelper = new DBhelper(getActivity());



        TextView txt = view.findViewById(R.id.txt);
        floatbtn = view.findViewById(R.id.floatbtn);


        ArrayList<String> title = new ArrayList();
        ArrayList<String> chapter = new ArrayList();
        ArrayList<String> lesson = new ArrayList();




        //getting the dialog fields
        View mydialog = getLayoutInflater().inflate(R.layout.takenotes,null);
        EditText mytitle = mydialog.findViewById(R.id.mytitle);
        EditText myversus = mydialog.findViewById(R.id.biblev);
        EditText notes = mydialog.findViewById(R.id.notes);
        Button addnotes = mydialog.findViewById(R.id.addrecord);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(mydialog);
        builder.setTitle("New Record");
        AlertDialog alertDialog= builder.create();

        floatbtn.setOnClickListener(view1 -> alertDialog.show());

        addnotes.setOnClickListener(view12 -> {
            alertDialog.dismiss();

            if (mytitle.getText().toString().isEmpty() && myversus.getText().toString().isEmpty() && notes.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Empty fields", Toast.LENGTH_SHORT).show();

            }else {

                Boolean checkinsertion = mydbhelper.insertnotes(mytitle.getText().toString(), myversus.getText().toString(), notes.getText().toString());

                if (checkinsertion == true) {
                    Toast.makeText(getActivity(), "Note taken successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Taking notes failed", Toast.LENGTH_SHORT).show();
                }
                mytitle.setText(" ");
                myversus.setText(" ");
                notes.setText(" ");
            }
        });

        // get SQLite database data

        Cursor get = mydbhelper.getNotes();

        if (get.getCount()>0){
            txt.setVisibility(View.GONE);

            while (get.moveToNext()){

                title.add(get.getString(1));
                chapter.add(get.getString(2));
                lesson.add(get.getString(3));

                MyListAdapter myadapter = new MyListAdapter(getActivity(),title,chapter,lesson);
                ListView list = view.findViewById(R.id.mylistview);
                list.setAdapter(myadapter);
            }
        }else{
            txt.setText("No summarises yet 🙄🙄");
        }

        return view;

    }

}