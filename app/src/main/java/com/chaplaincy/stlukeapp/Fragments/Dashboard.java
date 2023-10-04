package com.chaplaincy.stlukeapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.chaplaincy.stlukeapp.R;
import com.chaplaincy.stlukeapp.DashboardActivities.Hymns;
import com.chaplaincy.stlukeapp.DashboardActivities.Legion;
import com.chaplaincy.stlukeapp.DashboardActivities.Rosary;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        TextView name = findViewById(R.id.name);
        CardView hymns = findViewById(R.id.hymns);
        CardView legion = findViewById(R.id.legion);
        CardView rosary = findViewById(R.id.holyrosary);
        CardView novena = findViewById(R.id.novena);
        CardView readings = findViewById(R.id.dailyreading);
        CardView ordermass = findViewById(R.id.ordermass);

        ordermass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Still in process", Toast.LENGTH_SHORT).show();
            }
        });

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Still under formation", Toast.LENGTH_SHORT).show();
            }
        });
        novena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "Still under development", Toast.LENGTH_SHORT).show();
            }
        });

        rosary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(), Rosary.class);
                startActivity(nxt);
            }
        });

        legion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(), Legion.class);
                startActivity(nxt);
            }
        });

        hymns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent nxt = new Intent(getApplicationContext(), Hymns.class);
                startActivity(nxt);
//

            }
        });
    }
}