package com.example.newbrainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view3);
        WebView webView3 = findViewById(R.id.webview3);
        webView3.getSettings().setJavaScriptEnabled(true);
        webView3.getSettings().setDomStorageEnabled(true);
        webView3.setWebViewClient(new WebViewClient());
        webView3.loadUrl(HttpUtil.IP + "/month");
    }
}
