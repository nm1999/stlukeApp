package com.chaplaincy.stlukeapp.DashboardActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.chaplaincy.stlukeapp.R;

import java.io.InputStream;

public class Rosary extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rosary);
        TextView txt = findViewById(R.id.litany);

        String litanyofmary = "";
        try{
            InputStream inputStream4 = getAssets().open("litanyofmary.txt");
            int size4 = inputStream4.available();
            byte[] buffer4 = new byte[size4];
            inputStream4.read(buffer4);
            litanyofmary = new String(buffer4);

        } catch (Exception e) {
            e.printStackTrace();
        }

        txt.setText(Html.fromHtml(litanyofmary));
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
