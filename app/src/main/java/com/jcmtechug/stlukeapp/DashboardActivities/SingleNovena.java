package com.jcmtechug.stlukeapp.DashboardActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jcmtechug.stlukeapp.R;


public class SingleNovena extends AppCompatActivity {
    private TextView title,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_novena);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), Novena.class);
                startActivity(back);
            }
        });

        title = findViewById(R.id.singlenovenatitle);
        content = findViewById(R.id.content);

        Intent getinfo = getIntent();
        String mytitle = getinfo.getStringExtra("title");
        String mycontent = getinfo.getStringExtra("novena_content");

        content.setText(Html.fromHtml(mycontent));
        title.setText(mytitle);


    }
}