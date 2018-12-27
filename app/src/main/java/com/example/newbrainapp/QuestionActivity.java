package com.example.newbrainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private String[] quesData = {"AOFAS踝与后足功能评分", "Berg平衡量表", "JOA颈椎评分"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(QuestionActivity.this,
                android.R.layout.simple_list_item_1, quesData);
        ListView listView = findViewById(R.id.questionnaire_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickItem = quesData[position];
                switch (clickItem) {
                    case "AOFAS踝与后足功能评分":
                        Intent intent1 = new Intent(QuestionActivity.this, EvaluateForm01.class);
                        startActivity(intent1);
                        break;
                    case "Berg平衡量表":
                        Intent intent2 = new Intent(QuestionActivity.this, EvaluateForm02.class);
                        startActivity(intent2);
                        break;
                    case "JOA颈椎评分":
                        Intent intent3 = new Intent(QuestionActivity.this, EvaluateForm03.class);
                        startActivity(intent3);
                        break;
                    default:
                }
            }
        });
    }

}
