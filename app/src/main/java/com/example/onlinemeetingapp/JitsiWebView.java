package com.example.onlinemeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;

public class JitsiWebView extends AppCompatActivity {

    WebView jitsiwebview;
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jitsi_web_view);
        jitsiwebview=(WebView) findViewById(R.id.jitsiwebview);
        jitsiwebview.setWebViewClient(new WebViewClient());
        jitsiwebview.loadUrl("https://meet.jit.si/");


        WebSettings webSettings= jitsiwebview.getSettings();
        webSettings.setUserAgentString(null);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        CookieManager cookieManager= CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

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