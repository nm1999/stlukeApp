package com.jcmtechug.stlukeapp.DashboardActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jcmtechug.stlukeapp.DBHelper.DBhelper;
import com.jcmtechug.stlukeapp.Fragments.Profile;
import com.jcmtechug.stlukeapp.Fragments.Testimonies_and_prayer_requests;
import com.jcmtechug.stlukeapp.Mainmenu;
import com.jcmtechug.stlukeapp.Notes;
import com.jcmtechug.stlukeapp.R;
import com.jcmtechug.stlukeapp.SyncingActivity;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //handling those who had the old version of the application.
        // NOTE : their data is in sqlite
        DBhelper mydbhelper = new DBhelper(this);
        Cursor cursor = mydbhelper.getData();

        if (cursor.getCount() > 0) {
            //utlising the method in the login class to send user data to the server.
            SharedPreferences sharedPreferences = getSharedPreferences("stluke_app", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("user_id",0);

            if(userId < 1){
                Intent intent = new Intent(getApplicationContext(), SyncingActivity.class);
                startActivity(intent);
                finish();
            }
        }

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