package com.chaplaincy.stlukeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditNote extends AppCompatActivity {

    private EditText title,versus,note;
    private Button update_btn;
    private DBhelper mydb;
    private String note_id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mydb = new DBhelper(getApplicationContext());

        Intent intent = getIntent();
        note_id = intent.getStringExtra("id");
        String old_title = intent.getStringExtra("title");
        String old_versus = intent.getStringExtra("versus");
        String old_note = intent.getStringExtra("note");

        //get inputs
        title = findViewById(R.id.title);
        versus = findViewById(R.id.versus);
        note = findViewById(R.id.note);
        update_btn = findViewById(R.id.updateRecord);

        title.setText(old_title);
        versus.setText(old_versus);
        note.setText(old_note);

        update_btn.setOnClickListener(v -> {
            String new_title = title.getText().toString();
            String new_versus = versus.getText().toString();
            String new_note = note.getText().toString();
            int sync_state = 0;

            Log.i("click","clickkkkkkkkkk");


            Boolean res = mydb.editnote(note_id,new_title,new_versus,new_note,sync_state);
            Log.i("res", String.valueOf(res));

            if (res){
                new SweetAlertDialog(EditNote.this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Sucess")
                        .setConfirmButton("Okay", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        }).show();
            }else{
                new SweetAlertDialog(EditNote.this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failure")
                        .setContentText("Update could not complete. Try again.")
                        .setConfirmButton("Okay", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.hide();
                            }
                        }).show();
            }

        });




    }
}