package com.jcmtechug.stlukeapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.jcmtechug.stlukeapp.DBHelper.DBhelper;
import com.jcmtechug.stlukeapp.DashboardActivities.HomeActivity;
import com.jcmtechug.stlukeapp.EditNote;
import com.jcmtechug.stlukeapp.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private String[] title;
    private String[] chapter;
    private String[] mynotes;
    private String [] id;
    private Integer [] sync_status;
    LayoutInflater inflater;

    private DBhelper mydb;
    

    public MyListAdapter(Context ctx,ArrayList<String> id, ArrayList<String> title, ArrayList<String> mychapter ,ArrayList<String> note , ArrayList<Integer> sync){
        this.context = ctx;
        this.chapter = mychapter.toArray(new String[0]);
        this.title = title.toArray(new String[0]);
        this.mynotes = note.toArray(new String[0]);
        this.id= id.toArray(new String[0]);
        this.sync_status = sync.toArray(new Integer[]{});
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.myrecords,null);
        
        TextView mynotetitle = view.findViewById(R.id.title1);
        TextView mychapter = view.findViewById(R.id.chapter);
        TextView note = view.findViewById(R.id.lessoncontent);
        ImageView edit_icon = view.findViewById(R.id.edit_note);
        ImageView delete_icon = view.findViewById(R.id.delete_note);
        ImageView sync_icon = view.findViewById(R.id.sync_status);

        mynotetitle.setText(title[i]);
        mychapter.setText(chapter[i]);
        note.setText(mynotes[i]);
        if (sync_status[i] ==1){
            Glide.with(context).load(R.drawable.synced).into(sync_icon);
        }else{
            Glide.with(context).load(R.drawable.not_synced).into(sync_icon);
        }


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
                                new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE).setTitleText("sucess").setConfirmButton("Okay", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        context.startActivity(new Intent(context, HomeActivity.class));
                                    }
                                }).show();
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
            intent.putExtra("title",title[i]);
            intent.putExtra("versus",chapter[i]);
            intent.putExtra("note",mynotes[i]);
            context.startActivity(intent);

        });


        return view;
    }
}
