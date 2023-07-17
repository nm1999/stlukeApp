package com.chaplaincy.stlukeapp.DashboardActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;
import com.chaplaincy.stlukeapp.R;


public class Legion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legion);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(nxt);
            }
        });
    }
}