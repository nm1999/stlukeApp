package com.chaplaincy.stlukeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
//        getSupportActionBar().hide();

        login = findViewById(R.id.loginbtn);
        register = findViewById(R.id.register_link);

        DBhelper mydbhelper = new DBhelper(this);
        Cursor cursor = mydbhelper.getData();

        if (cursor.getCount()>0){
            Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(nxt);
            finish();

        }else{
            Toast.makeText(this, "SignUp please", Toast.LENGTH_SHORT).show();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),Login.class);
                startActivity(nxt);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),Login.class);
                startActivity(nxt);
            }
        });
    }
}