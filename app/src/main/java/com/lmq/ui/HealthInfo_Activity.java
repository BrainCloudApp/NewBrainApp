package com.lmq.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newbrainapp.R;
import com.google.gson.Gson;
import com.lmq.base.BaseActivity;
import com.lmq.common.CommonPresenter;
import com.lmq.common.CommonView;
import com.lmq.tool.SpacesItemDecoration;
import com.lmq.ui.adapter.MessageListAdapter;
import com.lmq.ui.adapter.UserHealthInfoAdapter;
import com.lmq.ui.entity.AppMessage;
import com.lmq.ui.entity.HealthInfo;
import com.lmq.ui.entity.HealthProblem;
import com.lmq.ui.entity.Health_Base;
import com.lmq.ui.entity.HospitalHistory;
import com.lmq.ui.entity.Partner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class HealthInfo_Activity extends BaseActivity implements CommonView{

    ArrayList<Partner> source=new ArrayList<>();
    CommonPresenter mpresenter=new CommonPresenter(this,this);
    private static  final int TAG_REFRESH=0;
    private  int requestTag=0;//0刷新，1 加载更多
    String id_pat="";//患者id
    HealthInfo info;
    @Override
    protected int setContentView(){
        return R.layout.activity_messagelist;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        try {
            setTitle("健康档案");
            id_pat=getIntent().getStringExtra("id");
            if(id_pat==null)
                id_pat="";
           // mpresenter.getUserHealthInfo(id_pat);
            initLocalData();
            initrefresh();
            setListView();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onResult(String result){
        try {
            switch (requestTag) {
                case TAG_REFRESH:
                    info =new Gson().fromJson(result, HealthInfo.class);
                            refreshLayout.finishRefresh(true);
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    UserHealthInfoAdapter sa;

    @BindView(R.id.refreshLayout)SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)RecyclerView recyclerView;


    public void initrefresh(){//上拉加载下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //refreshlayout.finishRefresh(2000/**,false*/);//传入false表示刷新失败
                mpresenter.getUserHealthInfo(id_pat);
            }
        });
      /*  refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000*//**,false*//*);//传入false表示加载失败
            }
        });*/
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
       // refreshLayout.setRefreshFooter(new ClassicsFooter(this));
    }
    public void setListView(){
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        //divider.setDrawable(new ColorDrawable(Color.rgb(204,204,204)));
        //divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.transparentdrawable));

       // recyclerView.addItemDecoration(divider);

        recyclerView.addItemDecoration(new SpacesItemDecoration(15));



        sa = new UserHealthInfoAdapter(info,mContext);

        recyclerView.setAdapter(sa);

    }
    public void initLocalData(){

        info=new HealthInfo();
        Health_Base base=new Health_Base();
        base.setName("张三");
        base.setImg(R.drawable.user1+"");
        base.setSex("男");
        base.setBirth("2001.12.12");
        base.setPhone("13888888888");
        base.setHealthstatus("亚健康");
        base.setHealthproblem("精神抑郁！");
        info.setBase(base);
        ArrayList<HealthProblem> problems=new ArrayList<>();
        HealthProblem h1=new HealthProblem();
        h1.setTime("2018.12.1");
        h1.setName("感冒");
        h1.setStatus("轻微");
        h1.setResult("治好了");
        problems.add(h1);
        HealthProblem h2=new HealthProblem();
        h2.setTime("2018.10.1");
        h2.setName("鼻炎");
        h2.setResult("没治好");
        h2.setStatus("严重");
        h2.setBeizhu("复发");
        problems.add(h2);
        ArrayList<HospitalHistory> hospitalHistories=new ArrayList<>();
        HospitalHistory hs=new HospitalHistory();
        hs.setIntime("2018.1.2");
        hs.setOuttime("2018.1.8");
        hs.setDoctor("张医生");
        hs.setChufang("sdhfjkdshfkj");
        hospitalHistories.add(hs);
        info.setHealthhospitals(hospitalHistories);
        info.setHealthProblems(problems);


    }


}
