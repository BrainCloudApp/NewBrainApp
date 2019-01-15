package com.example.newbrainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lmq.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssessAndRecover extends BaseActivity {

    private List<RecoverTargetBean> recoverTargetList = new ArrayList<>();

    @Override
    protected void initBundleData() {

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_assess_and_recover;
    }

    @Override
    protected void initView() {
        setTitle("评估及康复情况");
    }

    @BindView(R.id.recover_target_list) RecyclerView recoverTargetRecyclerView;

    @OnClick({R.id.TrainStat, R.id.ass_form})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.TrainStat:
                Intent intent1 = new Intent(this, WebView1.class);
                startActivity(intent1);
                break;
            case R.id.ass_form:
                Intent intent2 = new Intent(this, EvaluateFormList.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_and_recover);

        ButterKnife.bind(this);

        initList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recoverTargetRecyclerView.setLayoutManager(layoutManager);
        RecoverTargetAdapter adapter = new RecoverTargetAdapter(recoverTargetList);
        recoverTargetRecyclerView.setAdapter(adapter);
    }

    private void initList(){
        RecoverTargetBean recoverTarget1 = new RecoverTargetBean("2019-01-05", "完成训练");
        recoverTargetList.add(recoverTarget1);
        RecoverTargetBean recoverTarget2 = new RecoverTargetBean("2019-01-11", "减轻疼痛");
        recoverTargetList.add(recoverTarget2);
    }
}
