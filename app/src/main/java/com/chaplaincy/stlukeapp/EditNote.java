package com.chaplaincy.stlukeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txtvId = findViewById(R.id.note_id);
        txtvId.setText(id);
    }
}