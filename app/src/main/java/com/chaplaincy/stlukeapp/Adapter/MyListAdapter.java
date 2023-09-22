package com.chaplaincy.stlukeapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.EditNote;
import com.chaplaincy.stlukeapp.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private String[] title;
    private String[] chapter;
    private String[] mynotes;
    private String [] id;
    LayoutInflater inflater;

    private DBhelper mydb;
    

    public MyListAdapter(Context ctx,ArrayList<String> id, ArrayList<String> title, ArrayList<String> mychapter ,ArrayList<String> note){
        this.context = ctx;
        this.chapter = mychapter.toArray(new String[0]);
        this.title = title.toArray(new String[0]);
        this.mynotes = note.toArray(new String[0]);
        this.id= id.toArray(new String[0]);
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
        ImageView edit_icon = view.findViewById(R.id.edit_note);
        ImageView delete_icon = view.findViewById(R.id.delete_note);

        mynotetitle.setText(title[i]);
        mychapter.setText(chapter[i]);
        note.setText(mynotes[i]);

        delete_icon.setOnClickListener(v -> {
            new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Confirm")
                    .setContentText("Do you really want to delete this note ?")
                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.hide();
                        }
                    })
                    .setConfirmButton("Continue", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            mydb = new DBhelper(context);
                            Boolean res = mydb.delete(id[i]);
                            if (res){
                                sweetAlertDialog.hide();
                                new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE).setTitleText("sucess").show();
                            }else{
                                Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })

                    .show();
            ;
        });

        edit_icon.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditNote.class);
            intent.putExtra("id",id[i]);
            context.startActivity(intent);

        });


        return view;
    }
}
