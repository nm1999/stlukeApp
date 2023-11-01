package com.jcmtechug.stlukeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jcmtechug.stlukeapp.DashboardActivities.HomeActivity;

public class Read_more_Testimonies extends AppCompatActivity {

    private TextView title,username,created_at,story;
    private ImageView back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more_testimonies);

        title = findViewById(R.id.title);
        story = findViewById(R.id.story);
        created_at = findViewById(R.id.created_at);
        username = findViewById(R.id.username);
        back = findViewById(R.id.back_arrow2);

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("title"));
        username.setText(intent.getStringExtra("username"));
        created_at.setText(intent.getStringExtra("created_at"));
        story.setText(intent.getStringExtra("story"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

    }
}