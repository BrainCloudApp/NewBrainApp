package com.example.newbrainapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NearHospital extends AppCompatActivity {

    private String[] hospitalData = {"四川护理职业学院附属医院", "成都市龙泉驿区第一人民医院",
            "龙泉长康医院", "龙泉龙都医院", "龙泉长兴医院", "龙泉龙桥医院"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_hospital);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(NearHospital.this,
                android.R.layout.simple_list_item_1, hospitalData);
        ListView listView = findViewById(R.id.near_hospital);
        listView.setAdapter(adapter);
    }
}
