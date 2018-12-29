package com.lmq.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;
import com.lmq.ui.adapter.PartnerAdapter;
import com.lmq.ui.adapter.PartnerAdapter2;
import com.lmq.ui.entity.Partner;
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

public class PartnerHelp_Activity extends BaseActivity{

    ArrayList<Partner> source=new ArrayList<>();
    @Override
    protected int setContentView(){
        return R.layout.activity_partner;
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


    PartnerAdapter sa;

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


        sa = new PartnerAdapter(source,mContext);
       /* sa.setOnRecyclerViewListener(new PartnerAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

            }

        });*/
        recyclerView.setAdapter(sa);
    }
    public void initLocalData(){

        Partner p=new Partner();
        p.setHeadimg("");
        p.setId("1");
        p.setName("张三");
        p.setHealthinfo("健康状况一般");
        p.setPracticelist("今日已完成训练项目23次");
        ShareInfo share=new ShareInfo();
        share.setDianzancount(12);
        share.setSharecontent("今天完成了训练感觉很开心");
        share.setShareimgs(R.drawable.ic_pinglun+"");
        share.setPinglunlist(null);
        p.setShareinfo(share);
        source.add(p);

        Partner p1=new Partner();
        p1.setHeadimg("");
        p1.setId("2");
        p1.setName("李四");
        p1.setHealthinfo("健康状况不怎么好");
        p1.setPracticelist("今日已完成训练项目2次");
        ShareInfo share1=new ShareInfo();
        share1.setDianzancount(0);
        share1.setSharecontent("天天锻炼保持住");
        share1.setShareimgs(R.drawable.banana+"");
        share1.setPinglunlist(null);
        p1.setShareinfo(share1);
        source.add(p1);


        Partner p2=new Partner();
        p2.setHeadimg("");
        p2.setId("3");
        p2.setName("王五");
        p2.setHealthinfo("健康状况很好");
        p2.setPracticelist("今日已完成训练项目45次");
        ShareInfo share2=new ShareInfo();
        share2.setDianzancount(87);
        share2.setSharecontent("今天完成了训练感觉很开心");
        share2.setShareimgs(R.drawable.grape+"");

        ArrayList<ShareComment> comments=new ArrayList<>();
        ShareComment shareComment=new ShareComment();
        shareComment.setUsername("李某某");
        shareComment.setCommentinfo("你要继续保持哦！");
        shareComment.setCommenttime("111");
        comments.add(shareComment);
        ShareComment shareComment2=new ShareComment();
        shareComment2.setUsername("王某某");
        shareComment2.setCommentinfo("我也要继续努力了！");
        shareComment2.setCommenttime("111");
        comments.add(shareComment2);

        share2.setPinglunlist(comments);
        p2.setShareinfo(share2);
        source.add(p2);



    }

    @OnClick(R.id.back)
    public void goback(){
        finish();
    }
    @OnClick(R.id.action)//
    public void sharexinde(){
        Intent it=new Intent(mContext,ShareXinde_Activity.class);
        startActivity(it);

    }
}
