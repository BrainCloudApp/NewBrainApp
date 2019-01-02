package com.lmq.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;
import com.lmq.common.Appstorage;
import com.lmq.ui.adapter.PartnerAdapter;
import com.lmq.ui.adapter.PersonAdapter;
import com.lmq.ui.entity.Partner;
import com.lmq.ui.entity.Person;
import com.lmq.ui.entity.ShareComment;
import com.lmq.ui.entity.ShareInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class PersonList_Activity extends BaseActivity implements Login_View{

    ArrayList<Person> source=new ArrayList<>();
    Login_Presenter mpresenter=new Login_Presenter(this,this);

    @Override
    protected int setContentView(){
        return R.layout.activity_person;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        try {
            initLocalData();
            initrefresh();
            setListView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    PersonAdapter sa;

    @BindView(R.id.refreshLayout)SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)RecyclerView recyclerView;


    public void initrefresh(){//上拉加载下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/**,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/**,false*/);//传入false表示加载失败
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
    }
    public void setListView(){
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        //divider.setDrawable(new ColorDrawable(Color.rgb(204,204,204)));
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.line));
        recyclerView.addItemDecoration(divider);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0)
                    closeKeyboard();
            }
        });


        sa = new PersonAdapter(source,mContext);
        sa.setOnRecyclerViewListener(new PersonAdapter.OnRecyclerViewListener() {
            @Override
            public void search(String keyworkd) {
                showMes("搜索");
                closeKeyboard();
            }

            @Override
            public void showTip(String mes) {
                showMes(mes);
            }

            @Override
            public void onItemClick(int position) {

                Intent it=new Intent(mContext,Person_Info_Activity.class);
                startActivity(it);
            }
        });
        recyclerView.setAdapter(sa);
        closeKeyboard();
    }
    public void initLocalData(){


        for(int i=0;i<20;i++){
            Person p=new Person();
            p.setName("张三"+i);
            p.setImg("");
            source.add(p);
        }


    }

    @OnClick(R.id.back)
    public void goback(){
        finish();
    }
   /* @OnClick(R.id.action)//
    public void sharexinde(){
       *//* Intent it=new Intent(mContext,ShareXinde_Activity.class);
        startActivity(it);*//*
        mpresenter.login(Appstorage.getLoginUserName(mContext),Appstorage.getLoginUserPwd(mContext,Appstorage.getLoginUserName(mContext)));

    }*/
    public void loginresult(String result){
        showMes(result);
    }
    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
