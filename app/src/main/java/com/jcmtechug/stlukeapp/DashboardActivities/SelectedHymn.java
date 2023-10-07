package com.jcmtechug.stlukeapp.DashboardActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jcmtechug.stlukeapp.R;

public class SelectedHymn extends AppCompatActivity {

    private TextView hymn_title,song;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_hymn);

        hymn_title = findViewById(R.id.hymn_title);
        song = findViewById(R.id.song);


        Intent intent = getIntent();
        String hymn = intent.getStringExtra("song");
        String hymn_no = intent.getStringExtra("hymn_no");
        String title = intent.getStringExtra("title");

        hymn_title.setText(hymn_no+". "+title);
        song.setText(hymn);

    }
}