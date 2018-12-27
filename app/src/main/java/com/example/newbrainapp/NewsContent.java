package com.example.newbrainapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsContent extends AppCompatActivity {

    public static final String NEWS_CONTENT_NAME = "news_content_name";
    public static final String NEWS_CONTENT_IMAGE_ID = "news_content_image_id";
    public static final String NEWS_CONTENT_TEXT = "news_content_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        Intent intent = getIntent();
        String news_content_Name = intent.getStringExtra(NEWS_CONTENT_NAME);
        String news_content_Text = intent.getStringExtra(NEWS_CONTENT_TEXT);
        String news_content_Image = intent.getStringExtra(NEWS_CONTENT_IMAGE_ID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView contentImageView = findViewById(R.id.newsContent_imageView);
        TextView contentText = findViewById(R.id.newsContent_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(news_content_Name);
        Log.d("news", "" + news_content_Image);
        Glide.with(this).load(HttpUtil.IP + news_content_Image).into(contentImageView);
        contentText.setText(news_content_Text);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
