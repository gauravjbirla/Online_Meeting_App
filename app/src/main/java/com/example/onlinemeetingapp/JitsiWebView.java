package com.example.onlinemeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JitsiWebView extends AppCompatActivity {

    WebView jitsiwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jitsi_web_view);
        jitsiwebview=(WebView) findViewById(R.id.jitsiwebview);
        jitsiwebview.setWebViewClient(new WebViewClient());
        jitsiwebview.loadUrl("https://meet.jit.si/");
        WebSettings webSettings= jitsiwebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(jitsiwebview.canGoBack())
        {
            jitsiwebview.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}