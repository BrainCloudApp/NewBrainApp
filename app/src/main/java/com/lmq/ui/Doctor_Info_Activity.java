package com.lmq.ui;

import android.widget.TextView;

import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class Doctor_Info_Activity extends BaseActivity{
    @Override
    protected int setContentView(){
        return R.layout.activity_doctor_info;
    }

    @Override
    protected void initBundleData() {

    }
    @OnClick(R.id.back)
    public void goback(){
        finish();
    }

    @BindView(R.id.title)TextView titleView;
    @Override
    protected void initView() {
        try {
            titleView.setText("用户信息");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.gochat)
    public void gochat(){
        showMes("进入聊天界面");
    }
}
