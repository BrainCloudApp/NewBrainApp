package com.example.newbrainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lmq.ui.PartnerHelp_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment2 extends Fragment {

    private StringBuffer videoResult;
    private StringBuffer result;
    private ArrayList<String> material_list = new ArrayList<>();
    private ArrayList<String> video_name_list = new ArrayList<>();
    private ArrayList<String> video_pic_list = new ArrayList<>();
    private ArrayList<String> video_addr_list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CardView distance_instrct = getActivity().findViewById(R.id.distance_instruction);
        distance_instrct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initVideoContent();
            }
        });

        CardView study_material = getActivity().findViewById(R.id.study_material);
        study_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initStudyMaterialList();
            }
        });

        CardView ques = getActivity().findViewById(R.id.questionnaire);
        ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent);
            }
        });
        CardView partner_help = getActivity().findViewById(R.id.partner_help);
        partner_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PartnerHelp_Activity.class);
                startActivity(intent);
            }
        });
        CardView near_hosp = getActivity().findViewById(R.id.loc_help);
        near_hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NearHospital.class);
                startActivity(intent);
            }
        });

    }

    public void initVideoContent() {
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/video", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment2", "访问服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    videoResult = new StringBuffer();
                    videoResult.append(response.body().string());
                    videoContentParseJsonObject(videoResult.toString());
                    VideoConstant.videoTitles = video_name_list.toArray(new String[] {});
                    VideoConstant.videoThumbs = video_pic_list.toArray(new String[] {});
                    VideoConstant.videoUrls = video_addr_list.toArray(new String[] {});
                    Intent intent = new Intent(getActivity(), VideoList.class);
                    startActivity(intent);
                    video_addr_list.clear();
                    video_name_list.clear();
                    video_pic_list.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void videoContentParseJsonObject(String jsonData) {
        try {
            Log.d("Video", jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String video_name = jsonObject.getString("video_name");
                String video_addr = HttpUtil.IP + jsonObject.getString("video_addr");
                String video_pic = HttpUtil.IP + jsonObject.getString("video_pic");
                Log.d("video_addr", video_addr);
                Log.d("video_pic", video_pic);
                video_name_list.add(video_name);
                video_addr_list.add(video_addr);
                video_pic_list.add(video_pic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initStudyMaterialList(){
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/equip", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment2", "访问服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    result = new StringBuffer();
                    result.append(response.body().string());
                    parseJsonObject(result.toString());
//                    Log.d("Fragment1", "result; " + result.toString());
                    Intent intent = new Intent(getActivity(), StudyMaterial.class);
                    intent.putStringArrayListExtra("material_name", material_list);
                    startActivity(intent);
                    material_list.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void parseJsonObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String material_name = jsonObject.getString("name");
                material_list.add(material_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
