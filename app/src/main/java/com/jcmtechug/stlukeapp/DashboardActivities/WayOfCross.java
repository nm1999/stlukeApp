package com.jcmtechug.stlukeapp.DashboardActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jcmtechug.stlukeapp.R;

import java.io.InputStream;

public class WayOfCross extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_of_cross);

        TextView txt = findViewById(R.id.waycontent);
        String data = "";

        try {
            InputStream inputStream = getAssets().open("waycross.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            data = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txt.setText(Html.fromHtml(data));

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