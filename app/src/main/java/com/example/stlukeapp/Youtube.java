package com.example.stlukeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Youtube extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(nxt);
            }
        });
        LinearLayout linearLayout = findViewById(R.id.defaultview);
        WebView web = findViewById(R.id.web);
        web.loadUrl("https://www.youtube.com/results?search_query=online++catholics");
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        web.getSettings().setJavaScriptEnabled(true);

        if (web.canGoBack()){
            web.goBack();
        }
    }
}