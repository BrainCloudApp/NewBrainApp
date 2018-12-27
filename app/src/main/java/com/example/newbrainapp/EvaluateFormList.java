package com.example.newbrainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EvaluateFormList extends AppCompatActivity {

    private String[] evaListData = {"JOA颈椎评分", "Fugl-Meyer平衡量表", "Levine腕管综合征问卷"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_form_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(EvaluateFormList.this,
                android.R.layout.simple_list_item_1, evaListData);
        ListView listView = findViewById(R.id.evaluate_form_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickItem = evaListData[position];
                switch (clickItem) {
                    case "JOA颈椎评分":
                        Intent intent1 = new Intent(EvaluateFormList.this, EvaluateForm03.class);
                        startActivity(intent1);
                        break;
                    case "Fugl-Meyer平衡量表":
                        Intent intent2 = new Intent(EvaluateFormList.this, EvaluateForm04.class);
                        startActivity(intent2);
                        break;
                    case "Levine腕管综合征问卷":
                        Intent intent3 = new Intent(EvaluateFormList.this, EvaluateForm05.class);
                        startActivity(intent3);
                        break;
                    default:
                }
            }
        });
    }
}
