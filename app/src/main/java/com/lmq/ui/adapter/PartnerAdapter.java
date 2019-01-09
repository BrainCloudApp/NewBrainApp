package com.lmq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbrainapp.R;
import com.lmq.ui.entity.Partner;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class PartnerAdapter extends RecyclerView.Adapter {
   public static interface OnRecyclerViewListener {
        void onHeadClick(int position);
        void onDianZanClick(int position);
        void onPinglunClick(int position);
        void search(String keyworkd);
        void showTip(String mes);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = PartnerAdapter.class.getSimpleName();
    private ArrayList<Partner> source;
    private Context mcontext;

    public PartnerAdapter(ArrayList<Partner> list, Context mcontext) {
        this.source = list;
        this.mcontext=mcontext;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_partner, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ParterHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        try {
            if(position==0) {
              final   ParterHolder holder = (ParterHolder) viewHolder;
                holder.searchlinear.setVisibility(View.VISIBLE);
                holder.xindelinear.setVisibility(View.GONE);

                holder.keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH||actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                            //TODO回车键按下时要执行的操作
                            if (TextUtils.isEmpty(holder.keyword.getText().toString().trim())){

                                onRecyclerViewListener.showTip("请输入患者名字！");
                                return true;
                            }else {
                                onRecyclerViewListener.search(holder.keyword.getText().toString().trim());
                            }
                            return true;
                        }
                        return false;
                    }
                });


                if(holder.keyword.getText().toString().trim().length()>0){
                    holder.cancle.setVisibility(View.VISIBLE);
                }else
                    holder.cancle.setVisibility(View.GONE);

            }else{
                ParterHolder holder = (ParterHolder) viewHolder;
                holder.searchlinear.setVisibility(View.GONE);
                holder.xindelinear.setVisibility(View.VISIBLE);
                holder.position = position-1;

                holder.img.setImageResource(R.drawable.user1);
                holder.username.setText(source.get(position-1).getName());
                //  holder.practice_info.setText(source.get(i).getPracticelist());

                holder.shareimg.setImageResource(Integer.valueOf(source.get(position-1).getShareinfo().getShareimgs()));//应该获取服务端数据
                holder.shareinfo.setText(source.get(position-1).getShareinfo().getSharecontent());
                int dianzanno = source.get(position-1).getShareinfo().getDianzancount();
                holder.dianzan.setText(dianzanno == 0 ? "" : (dianzanno + ""));

                String pingluninfostr = source.get(position-1).getShareinfo().getpingluninfostr();
                holder.pingluninfo.setText(pingluninfostr);
                if (pingluninfostr.length() == 0)
                    holder.pingluninfo.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return source.size()+1;
    }

   /* class  SearchHolder extends RecyclerView.ViewHolder{
        public SearchHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
        @BindView(R.id.keyword)EditText keyword;
        @BindView(R.id.cancle)Button cancle;

    }*/
    class ParterHolder extends RecyclerView.ViewHolder
    {
        public int position;
        public ParterHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
        @BindView(R.id.keyword)EditText keyword;
        @BindView(R.id.cancle)Button cancle;
        @BindView(R.id.practice_info) TextView practice_info;
        @BindView(R.id.username) TextView username;
        @BindView(R.id.user_image)CircleImageView img;
        @BindView(R.id.shareinfo)TextView shareinfo;
        @BindView(R.id.dianzan)TextView dianzan;
        @BindView(R.id.pinglun)TextView pinglun;
        @BindView(R.id.shareimg)ImageView shareimg;
        @BindView(R.id.linear_search)LinearLayout searchlinear;
        @BindView(R.id.linear_xinde)LinearLayout  xindelinear;
       // @BindView(R.id.dianzancount)TextView dianzancount;
        @BindView(R.id.pingluninfo)TextView pingluninfo;
        @OnClick(R.id.user_image)
        public void clickHead(){
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onHeadClick(position);
            }
        }
        @OnClick(R.id.dianzan)
        public void clickdianzan(){
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onDianZanClick(position);
            }
        }
        @OnClick(R.id.pinglun)
        public void clickpinglun(){
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onPinglunClick(position);
            }
        }

    }

}