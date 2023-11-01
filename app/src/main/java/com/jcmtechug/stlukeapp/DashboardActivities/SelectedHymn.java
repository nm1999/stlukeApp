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
        String title = intent.getStringExtra("title");
        String songBody = intent.getStringExtra("song");

        hymn_title.setText(title);
        song.setText(songBody);







    }
}