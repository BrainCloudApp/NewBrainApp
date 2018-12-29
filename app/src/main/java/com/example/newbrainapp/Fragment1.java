package com.example.newbrainapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment1 extends Fragment {

    private List<News> newsList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private NewsAdapter newsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsList = (ArrayList<News>) getActivity().getIntent().getSerializableExtra("news");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_fragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(newsAdapter);

        swipeRefresh = getActivity().findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContents();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    public void initNews(){
//        for (int i = 0; i < 3; i++){
//            News news1 = new News("news1", R.drawable.apple_pic);
//            news1.setNewsContent("111111111111111111111111111111111111");
//            newsList.add(news1);
//            News news2 = new News("news2", R.drawable.orange_pic);
//            news2.setNewsContent("222222222222222222222222222222222222222222222");
//            newsList.add(news2);
//            News news3 = new News("news3", R.drawable.pear_pic);
//            newsList.add(news3);
//            News news4 = new News("news4", R.drawable.banana_pic);
//            newsList.add(news4);
//            News news5 = new News("news5", R.drawable.cherry_pic);
//            newsList.add(news5);
//            News news6 = new News("news6", R.drawable.grape_pic);
//            newsList.add(news6);
//        }
//    }

    private void refreshContents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news", new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fragment1", "刷新失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String update_result = response.body().string();
                            newsList.clear();
                            parseJsonObject(update_result);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    newsAdapter.notifyDataSetChanged();
                                    swipeRefresh.setRefreshing(false);
                                    Log.d("Fragment1_refresh_stop", newsList.toString());
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public void parseJsonObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("Fragment1", jsonObject.toString());
                String title = jsonObject.getString("title");
                String picture = jsonObject.getString("img");
//                String con = jsonObject.getString("con");
//                Content content = new Content(title, picture, con);
                News content = new News(title,picture);
                newsList.add(content);
//                Log.d("Fragment1","title: " + title);
//                Log.d("Fragment1","picture: " + picture);
//                Log.d("Fragment1","content: " + con);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
