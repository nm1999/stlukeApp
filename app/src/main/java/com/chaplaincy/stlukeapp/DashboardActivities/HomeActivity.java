package com.chaplaincy.stlukeapp.DashboardActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.chaplaincy.stlukeapp.Fragments.Profile;
import com.chaplaincy.stlukeapp.Fragments.Testimonies_and_prayer_requests;
import com.chaplaincy.stlukeapp.Mainmenu;
import com.chaplaincy.stlukeapp.Notes;
import com.chaplaincy.stlukeapp.R;
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
                        selectedfrag = new Testimonies_and_prayer_requests();
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