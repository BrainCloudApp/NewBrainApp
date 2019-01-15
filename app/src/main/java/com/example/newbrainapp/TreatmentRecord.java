package com.example.newbrainapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lmq.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TreatmentRecord extends BaseActivity {

    private List<TreatmentRecordBean> treatRecordList = new ArrayList<>();

    @Override
    protected void initBundleData() {

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_treatment_record;
    }

    @Override
    protected void initView() {
        try {

            setTitle("治疗记录");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_record);
        initList();
        RecyclerView recyclerView = findViewById(R.id.treatment_record_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TreatmentRecordAdapter adapter = new TreatmentRecordAdapter(treatRecordList);
        recyclerView.setAdapter(adapter);
    }

    private void initList(){
        TreatmentRecordBean item1 = new TreatmentRecordBean("针灸","2018-12-30","完成");
        treatRecordList.add(item1);
        TreatmentRecordBean item2 = new TreatmentRecordBean("针灸","2019-1-11","完成");
        treatRecordList.add(item2);
        TreatmentRecordBean item3 = new TreatmentRecordBean("针灸","2019-1-12","完成");
        treatRecordList.add(item3);
    }
}
