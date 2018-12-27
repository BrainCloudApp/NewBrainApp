package com.example.newbrainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);
        WebView webView2 = findViewById(R.id.webview2);
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setDomStorageEnabled(true);
        webView2.setWebViewClient(new WebViewClient());
        webView2.loadUrl(HttpUtil.IP + "/info");
    }
}
