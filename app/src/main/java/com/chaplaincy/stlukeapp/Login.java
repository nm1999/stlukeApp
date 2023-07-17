package com.chaplaincy.stlukeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.chaplaincy.stlukeapp.DBHelper.DBhelper;
import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private Button register;
    private ArrayList<String> myarr;
    private TextView signin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        ListView listView = findViewById(R.id.listv);
        myarr = new ArrayList<>();

        EditText firstname = findViewById(R.id.firstName);
        EditText surname = findViewById(R.id.surName);
        EditText email = findViewById(R.id.emailaddress);
        EditText contact = findViewById(R.id.contact);
        signin = findViewById(R.id.has_account);

        TextInputLayout first_name = findViewById(R.id.first_name);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),SignIn.class);
                startActivity(nxt);
            }
        });

        DBhelper mydbhelper = new DBhelper(this);


        // acessing the data from the database

        Cursor cursor = mydbhelper.getData();
        if (cursor.getCount()>0){
//            Toast.makeText(this, "Already have an account", Toast.LENGTH_SHORT).show();
            Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(nxt);
            finish();

        }else{
            Toast.makeText(this, "SignUp please", Toast.LENGTH_SHORT).show();
        }

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname_str = firstname.getText().toString();
                String surname_str = surname.getText().toString();
                String email_str = email.getText().toString();
                String contact_str = contact.getText().toString();

                if (TextUtils.isEmpty(firstname_str)){
                    Toast.makeText(Login.this, "name is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surname_str)){
                    Toast.makeText(Login.this, "name is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email_str)){
                    Toast.makeText(Login.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contact_str)){
                    Toast.makeText(Login.this, "Contact is required", Toast.LENGTH_SHORT).show();
                    return;
                }


                Boolean res = mydbhelper.insertuser(firstname_str,surname_str,email_str,contact_str);

                if (res==true){
                    Toast.makeText(Login.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                    Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(nxt);
                }else {
                    Toast.makeText(Login.this, "NOt registered", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void viewdata() {

    }
}