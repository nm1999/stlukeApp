package com.chaplaincy.stlukeapp.DashboardActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;
import com.chaplaincy.stlukeapp.R;

import java.io.InputStream;

public class Catena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catena);

        TextView catena = findViewById(R.id.catena);
        String content = "";

        try {
            InputStream inputStream = getAssets().open("catena.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            content = new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        catena.setText(Html.fromHtml(content));

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(back);
            }
        });

    }
}