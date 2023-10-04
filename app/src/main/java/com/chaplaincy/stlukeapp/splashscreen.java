package com.chaplaincy.stlukeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaplaincy.stlukeapp.R;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);


        ImageView logo = findViewById(R.id.mylogo);
        TextView name = findViewById(R.id.logoname);
        LinearLayout dev = findViewById(R.id.developer);

        Animation one = AnimationUtils.loadAnimation(splashscreen.this,R.anim.lefttoright);
        Animation two = AnimationUtils.loadAnimation(splashscreen.this,R.anim.toptobottom);
        Animation d = AnimationUtils.loadAnimation(splashscreen.this,R.anim.righttoleft);

        logo.startAnimation(two);
        name.startAnimation(one);
        dev.startAnimation(d);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nxt = new Intent(getApplicationContext(),SignIn.class);
                startActivity(nxt);
                finish();
            }
        },2000);
    }
}