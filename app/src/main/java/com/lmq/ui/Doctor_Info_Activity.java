package com.lmq.ui;

import android.widget.TextView;

import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;
import com.lmq.common.CommonPresenter;
import com.lmq.common.CommonView;

import butterknife.BindView;
import butterknife.OnClick;

public class Doctor_Info_Activity extends BaseActivity implements CommonView{

    CommonPresenter mpresenter=new CommonPresenter(this,this);
    @Override
    protected int setContentView(){
        return R.layout.activity_doctor_info;
    }

    @Override
    protected void initBundleData() {

    }


    @BindView(R.id.title)TextView titleView;
    @Override
    protected void initView() {
        try {
            setTitle("用户信息");

            mpresenter.getDoctorInfo(getIntent().getStringExtra("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.gochat)
    public void gochat(){
        showMes("进入聊天界面");
    }
    public  void onResult(String result){

    }
}
