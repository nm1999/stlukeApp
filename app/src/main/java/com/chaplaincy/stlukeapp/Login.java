package com.chaplaincy.stlukeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



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
            Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(nxt);
            finish();

        }else{
            Toast.makeText(this, "SignUp please", Toast.LENGTH_SHORT).show();
        }

//        while(cursor.moveToNext()){
//            myarr.add(cursor.getString(0));
//
//            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,myarr);
//            listView.setAdapter(adapter);
//        }




        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Login.this, contact.getText().toString(), Toast.LENGTH_SHORT).show();

                if (firstname.getText().toString().isEmpty() && surname.getText().toString().isEmpty() && email.getText().toString().isEmpty() && contact.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Inputs are Empty", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "Inputs filled", Toast.LENGTH_SHORT).show();
                    Boolean res = mydbhelper.insertuser(firstname.getText().toString(),surname.getText().toString(),email.getText().toString(),contact.getText().toString());

                    if (res==true){
                        Toast.makeText(Login.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                        Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(nxt);
                    }else {
                        Toast.makeText(Login.this, "NOt registered", Toast.LENGTH_SHORT).show();
                    }

                    }

                }


//

        });
    }

    private void viewdata() {

    }
}