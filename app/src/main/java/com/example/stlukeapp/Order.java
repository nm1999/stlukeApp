package com.example.stlukeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView content = findViewById(R.id.order);
        String myorder = "";


        try {
            InputStream inputStream = getAssets().open("orderofmass.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            myorder = new String(buffer);
            content.setText(Html.fromHtml(myorder));

        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(nxt);
            }
        });
    }
}