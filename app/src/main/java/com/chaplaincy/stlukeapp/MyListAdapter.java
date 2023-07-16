package com.chaplaincy.stlukeapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private String[] title;
    private String[] chapter;
    private String[] mynotes;
    LayoutInflater inflater;
    

    public MyListAdapter(Context ctx, ArrayList<String> title, ArrayList<String> mychapter ,ArrayList<String> note){
        this.context = context;
        this.chapter = mychapter.toArray(new String[0]);
        this.title = title.toArray(new String[0]);
        this.mynotes = note.toArray(new String[0]);
        inflater = LayoutInflater.from(ctx);
    }
    
    
    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.myrecords,null);
        
        TextView mynotetitle = view.findViewById(R.id.title1);
        TextView mychapter = view.findViewById(R.id.chapter);
        TextView note = view.findViewById(R.id.lessoncontent);


        mynotetitle.setText(title[i]);
        mychapter.setText(chapter[i]);
        note.setText(mynotes[i]);

        return view;
    }
}
