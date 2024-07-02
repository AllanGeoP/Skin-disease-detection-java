package com.example.skind;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class Find_hospital extends AppCompatActivity {
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hospital);
        web=findViewById(R.id.web);
        WebSettings webSettings=web.getSettings();
        webSettings.setJavaScriptEnabled( true );
        web.loadUrl("https://www.google.com/maps/search/hospitals+near+by+ottapalam/@10.035507,76.2408218,10.74z?entry=ttu");
        web.setWebViewClient( new WebViewClient());
    }
}