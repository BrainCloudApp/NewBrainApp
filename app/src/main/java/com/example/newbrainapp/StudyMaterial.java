package com.example.newbrainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lmq.common.Appstorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StudyMaterial extends AppCompatActivity {

    private List<String> material_list = new ArrayList<>();
    private List<StudyMaterialBean> studyMaterialBeans = new ArrayList<>();
    private StringBuffer result;
    private StudyMaterialBean studyMaterialBean;
    ListView materialList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);
        Log.d("Equip", studyMaterialBeans.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(StudyMaterial.this,
                android.R.layout.simple_list_item_1, material_list);
        materialList = findViewById(R.id.material_list);
        materialList.setAdapter(adapter);

        materialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String name = materialList.getItemAtPosition(position) + "";
                studyMaterialBean = new StudyMaterialBean();
                studyMaterialBean.setName(name);
                initMaterialContent(name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initStudyMaterialList();
        String materialList = Appstorage.getStr(this,"StudyMaterialList");
        if (materialList.isEmpty())
            initStudyMaterialList();
        try {
            JSONArray jsonArray = new JSONArray(materialList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String equip_name = jsonObject.getString("name");
                material_list.add(equip_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initStudyMaterialList(){
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/equip", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Appstorage.saveList(StudyMaterial.this, "studyMaterial", result);
                studyMaterialBeans = Appstorage.getList(StudyMaterial.this, StudyMaterialBean.class, "studyMaterial");
            }
        });
    }



    public void initMaterialContent(String name) {
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/equip/" + name, new Callback() {
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

                    Intent intent = new Intent(StudyMaterial.this, StudyMaterialItem.class);
                    intent.putExtra("material_name",studyMaterialBean.getName());
                    intent.putExtra("material_pic",studyMaterialBean.getImg());
                    intent.putExtra("material_con",studyMaterialBean.getCon());
                    startActivity(intent);
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
                studyMaterialBean.setImg(jsonObject.getString("img"));
                studyMaterialBean.setCon(jsonObject.getString("con"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
