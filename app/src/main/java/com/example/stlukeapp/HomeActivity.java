package com.example.stlukeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomnav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Mainmenu()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfrag = null;
                switch (item.getItemId()){
                    case R.id.home:
                        selectedfrag = new Mainmenu();
                        break;
                    case R.id.message:
                        selectedfrag = new Requests();
                        break;
                    case R.id.notes:
                        selectedfrag = new Notes();
                        break;
                    case R.id.profile:
                        selectedfrag = new Profile();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,selectedfrag).commit();
                return true;
            }
        });

    }
}