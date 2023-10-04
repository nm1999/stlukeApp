package com.jcmtechug.stlukeapp.DashboardActivities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jcmtechug.stlukeapp.R;


public class Readings extends AppCompatActivity {
    private WebView web;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        linearLayout = findViewById(R.id.defaultview);
        ImageView back = findViewById(R.id.back);
        swipeRefreshLayout = findViewById(R.id.swipelayout);
        web = findViewById(R.id.web);

        if (isInternetConnected()){
            loadWebPage();
        }else{
            web.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(nxt);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternetConnected()){
                    loadWebPage();
                }else{
                    loadnoInternetPage();
                }
            }
        });

    }

    private void loadnoInternetPage() {
        web.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    private void loadWebPage() {
        web.loadUrl("https://bible.usccb.org/daily-bible-reading");
        web.getSettings().setJavaScriptEnabled(true);
        if (web.canGoBack()){
            web.goBack();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}