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

import com.lmq.common.Appstorage;
import com.lmq.tool.LmqTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment1 extends Fragment {

    private List<News> newsList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private NewsAdapter newsAdapter;
    private StringBuffer result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInitContent();
        newsList = Appstorage.getNewsList(getActivity());
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

    private void refreshContents() {
                HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news", new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fragment1", "刷新失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            final String update_result = response.body().string();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    newsList.clear();
                                    Appstorage.saveNewsList(getActivity(), update_result);
                                    newsList.addAll(LmqTool.jsonToArrayList(update_result, News.class));
                                    newsAdapter.notifyDataSetChanged();
                                    swipeRefresh.setRefreshing(false);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getInitContent() {
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment1", "服务器访问失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    result = new StringBuffer();
                    result.append(response.body().string());
                    Appstorage.saveNewsList(getActivity(), result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
