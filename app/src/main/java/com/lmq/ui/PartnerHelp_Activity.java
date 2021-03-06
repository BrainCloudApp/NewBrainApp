package com.lmq.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newbrainapp.R;
import com.lmq.base.BaseActivity;
import com.lmq.common.AppContact;
import com.lmq.common.Appstorage;
import com.lmq.common.CommonPresenter;
import com.lmq.common.CommonView;
import com.lmq.im.chatroom.activity.ChatRoomActivity;
import com.lmq.tool.LmqTool;
import com.lmq.tool.PermisstionCheck;
import com.lmq.ui.adapter.PartnerAdapter;
import com.lmq.ui.adapter.PartnerAdapter2;
import com.lmq.ui.entity.Partner;
import com.lmq.ui.entity.ShareComment;
import com.lmq.ui.entity.ShareInfo;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
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

public class PartnerHelp_Activity extends BaseActivity implements CommonView{

    ArrayList<Partner> source=new ArrayList<>();
    CommonPresenter mpresenter=new CommonPresenter(this,this);
    private static  final int TAG_REFRESH=0;
    private static  final int TAG_LOADMORE=1;
    private  int requestTag=0;//0刷新，1 加载更多
    private String keyword="";
    private boolean haspermission=false;
    @Override
    protected int setContentView(){
        return R.layout.activity_partner;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            haspermission=   PermisstionCheck.checkAndRequestPermission(PartnerHelp_Activity.this) ;
        }else{
            haspermission=true;
        }
        if(mpresenter.getActivity()==null)
            mpresenter=new CommonPresenter(this,this);
    }
    @Override
    protected void initView() {
        try {
            setTitle("同伴互助");
            keyword=getIntent().getStringExtra("keyword");
            if(keyword==null)
                keyword="";
            initLocalData();
            initrefresh();
            setListView();
            initPinglun();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onResult(String result){
        switch (requestTag){
            case TAG_REFRESH:
                refreshLayout.finishRefresh(true);
                break;
            case TAG_LOADMORE:
                refreshLayout.finishLoadMore(true);
                break;
        }
    }

    @Override
    public void showError(String err) {
        super.showError(err);
        switch (requestTag){
            case TAG_REFRESH:
                refreshLayout.finishRefresh(false);
                break;
            case TAG_LOADMORE:
                refreshLayout.finishLoadMore(false);
                break;
        }
    }
    PartnerAdapter sa;

    @BindView(R.id.refreshLayout)SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)RecyclerView recyclerView;


    public void initrefresh(){//上拉加载下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
               // refreshlayout.finishRefresh(2000/**,false*/);//传入false表示刷新失败
                mpresenter.getExprienceList(keyword);
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
                hidPinglun();
            }
        });


        sa = new PartnerAdapter(source,mContext);
        sa.setOnRecyclerViewListener(new PartnerAdapter.OnRecyclerViewListener(){

            @Override
            public void onHeadClick(int position) {
                //showMes("点击头像："+source.get(position).getName());
                Intent it=new Intent(mContext,Person_Info_Activity.class);
                startActivity(it);
            }

            @Override
            public void onDianZanClick(int position) {
                showMes("点击点赞："+source.get(position).getName());
            }

            @Override
            public void onPinglunClick(int position) {
               // showMes("点击评论："+source.get(position).getName());
                showPinglun();
            }

            @Override
            public void search(String keyworkd) {

                if(!LmqTool.isFastClick()) {
                    showMes("搜索名字");
                    closeKeyboard();
                    Intent it = new Intent(mContext, PersonList_Activity.class);
                    startActivity(it);
                }
            }

            @Override
            public void showTip(String mes) {

                showMes(mes);
            }

            @Override
            public void goChatRoom() {
               // showMes("进入聊天室");
                goCommunicat();
            }
        });
        recyclerView.setAdapter(sa);
        closeKeyboard();
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
        share1.setShareimgs(R.drawable.ic_pinglun+"");
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
        share2.setShareimgs(R.drawable.ic_pinglun+"");

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

        ShareComment shareComment3=new ShareComment();
        shareComment3.setUsername("周某某");
        shareComment3.setCommentinfo("哈哈哈！");
        shareComment3.setCommenttime("111");
        comments.add(shareComment3);

        share2.setPinglunlist(comments);
        p2.setShareinfo(share2);
        source.add(p2);



    }

    @OnClick(R.id.action)//
    public void sharexinde(){
        Intent it=new Intent(mContext,ShareXinde_Activity.class);
        startActivity(it);
      // mpresenter.login("张三","111");
       // mpresenter.getContent();
       // goLogin();
    }
    public void goLogin(){
        final LoginInfo info = new LoginInfo("acc_01", "111111");

      /*  NIMClient.getService(AuthService.class).login(info)
                .setCallback(new RequestCallback<LoginInfo>() {//sdk提供的手动登录方法
                    @Override
                    public void onSuccess(LoginInfo param) {

                        showMes("登录成功！");
                        NimUIKitImpl.setAccount(param.getAccount());
                        NimUIKit.startP2PSession(mContext,"acc_02");

                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });*/

        NimUIKit.login(info, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {

                //启动单聊界面
                showMes("登录成功！");
                NimUIKitImpl.setAccount(loginInfo.getAccount());
                NimUIKit.startP2PSession(mContext,"acc_02");
                // 启动群聊界面
                // NimUIKit.startTeamSession(MainActivity.this, "群ID");
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });


    }
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

    @BindView(R.id.linear_pinglun)LinearLayout linear_pinglun;
    @BindView(R.id.pinglunedit)EditText pinglunedit;
    public void initPinglun(){
        pinglunedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE||actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    if (TextUtils.isEmpty(pinglunedit.getText().toString().trim())){

                        showMes("请输入评论内容!");
                        return true;
                    }else {
                        pinglun();
                    }
                    return true;
                }
                return false;
            }
        });
        hidPinglun();
    }
    public void showPinglun(){
        linear_pinglun.setVisibility(View.VISIBLE);
        pinglunedit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(pinglunedit, InputMethodManager.SHOW_IMPLICIT);

    }
    public void hidPinglun(){
        closeKeyboard();
        linear_pinglun.setVisibility(View.GONE);

    }
    @OnClick(R.id.pinglun)
    public void pinglun(){
        String mes=pinglunedit.getText().toString().trim();
        if(mes.length()==0){
            showMes("请输入评论内容!");
            return;
        }
        showMes("提交评论内容："+mes);
        mpresenter.share_comment("id",mes);
        pinglunedit.setText("");

        hidPinglun();
    }
    public void goCommunicat(){
        String account = Appstorage.getIMUser_Account_Acc(this);
        String token = Appstorage.getIMUser_Account_Pwd(this);
        if (account.length()>0&&token.length()>0){
            //已登录过。直接进入聊天
            NimUIKitImpl.setAccount(account);
           // NimUIKit.startP2PSession(mContext, AppContact.localImDes);

            ChatRoomActivity.start(PartnerHelp_Activity.this,"8396");//使用官方demo的聊天室id
        }else {
            final LoginInfo info = new LoginInfo(AppContact.localImAccount, AppContact.localImPwd);
            NimUIKit.login(info, new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo loginInfo) {

                    //启动单聊界面
                    //  showMes("登录成功！");
                    Log.d("Login", "登录成功！");
                    NimUIKitImpl.setAccount(loginInfo.getAccount());
                 //   NimUIKit.startP2PSession(mContext, AppContact.localImDes);
                    ChatRoomActivity.start(PartnerHelp_Activity.this,"3001");//使用官方demo的聊天室id

                    // NimUIKit.startChatting(Login.this, "acc_02",SessionTypeEnum.P2P, null,null);
                    // 启动群聊界面
                    // NimUIKit.startTeamSession(MainActivity.this, "群ID");
                }

                @Override
                public void onFailed(int i) {
                    Log.d("Login", "登录失败！");
                }

                @Override
                public void onException(Throwable throwable) {
                    Log.d("Login", "登录异常！"+throwable.getMessage());
                }
            });
        }


    }
}
