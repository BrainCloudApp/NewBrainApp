package com.example.newbrainapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNews;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View newsView;
        ImageView newsImage;
        TextView newsName;

        public ViewHolder(View view){
            super(view);
            newsView = view;
            newsImage = view.findViewById(R.id.news_image);
            newsName = view.findViewById(R.id.news_name);
        }
    }

    public NewsAdapter(List<News> newsList){
        mNews = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final News news = mNews.get(position);
//                Toast.makeText(view.getContext(), "view" + news.getNewsName(), Toast.LENGTH_LONG).show();
                if("".equals(news.getNewsContent())) {
                    HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news/" + news.getTitle(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Fragment1", "服务器访问失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String resp = response.body().string();
                            try {
                                JSONObject respJson = new JSONObject(resp);
                                final String con = (String) respJson.get("con");
//                                Log.d("ContentAdapter_con", con);
                                news.setNewsContent(con);
                                Intent intent = new Intent(mContext, NewsContent.class);
                                intent.putExtra(NewsContent.NEWS_CONTENT_NAME, news.getTitle());
                                intent.putExtra(NewsContent.NEWS_CONTENT_IMAGE_ID, news.getImg());
                                intent.putExtra(NewsContent.NEWS_CONTENT_TEXT, news.getNewsContent());
                                mContext.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    Intent intent = new Intent(mContext, NewsContent.class);
                    intent.putExtra(NewsContent.NEWS_CONTENT_NAME, news.getTitle());
                    intent.putExtra(NewsContent.NEWS_CONTENT_IMAGE_ID, news.getImg());
                    intent.putExtra(NewsContent.NEWS_CONTENT_TEXT, news.getNewsContent());
                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

//    public void refresh(List<News> newsList){
//        this.mNews = newsList;
//        this.notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        try {
            News single_news = mNews.get(position);
            holder.newsName.setText(single_news.getTitle());
            Log.d("Fragment1",HttpUtil.IP + single_news.getImg());
            Glide.with(mContext).load(HttpUtil.IP + single_news.getImg()).into(holder.newsImage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
