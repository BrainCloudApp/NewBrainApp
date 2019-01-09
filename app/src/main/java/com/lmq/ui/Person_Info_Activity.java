package com.lmq.ui;

import android.content.Intent;
import android.widget.TextView;

import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;
import com.lmq.common.CommonPresenter;
import com.lmq.common.CommonView;

import butterknife.BindView;
import butterknife.OnClick;

public class Person_Info_Activity  extends BaseActivity implements CommonView{

    CommonPresenter mpresenter=new CommonPresenter(this,this);
    @Override
    protected int setContentView(){
        return R.layout.activity_person_info;
    }

    @Override
    protected void initBundleData() {

    }


    @Override
    protected void initView() {
        try {
           setTitle("用户信息");
            mpresenter.getPersonInfo(getIntent().getStringExtra("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @OnClick(R.id.gochat)
    public void gochat(){
        showMes("进入聊天界面");
    }
    @OnClick(R.id.linear_practice)
    public void goPractice(){
        showMes("进入训练项目");
    }
    @OnClick(R.id.linear_share)
    public void goShare(){

        Intent it=new Intent(mContext,PersonShareList_Activity.class);
        startActivity(it);

    }
    public  void onResult(String result){

    }
}
