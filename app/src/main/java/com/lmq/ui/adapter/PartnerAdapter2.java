package com.lmq.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbrainapp.R;
import com.lmq.ui.entity.Partner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PartnerAdapter2  extends BaseAdapter{
    private LayoutInflater mInflater;
    private Context mcontext;
    private ArrayList<Partner> source;
     public PartnerAdapter2(Context context, ArrayList<Partner> data ) {
        mInflater = LayoutInflater.from(context);
        this.source=data;
        mcontext=context;
        // this.mNativeExpressADView=nativeExpressADView;

    }

    public int getCount() {
        return source.size();
    }


    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.listitem_partner, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {

         /*  holder.img.setBackgroundResource(R.drawable.user1);
            holder.username.setText(source.get(position).getName());
            holder.practice_info.setText(source.get(position).getPracticelist());*/
/*
            holder.shareimg.setBackgroundResource(Integer.valueOf(source.get(position).getShareinfo().getShareimgs()));//应该获取服务端数据
            holder.shareinfo.setText(source.get(position).getShareinfo().getSharecontent());
            int dianzanno = source.get(position).getShareinfo().getDianzancount();
            holder.dianzancount.setText(dianzanno == 0 ? "" : (dianzanno + ""));

            String pingluninfostr = source.get(position).getShareinfo().getpingluninfostr();
            holder.pingluninfo.setText(pingluninfostr);
            if (pingluninfostr.length() == 0)
                holder.pingluninfo.setVisibility(View.GONE);
            holder.dianzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mcontext, "点赞！", Toast.LENGTH_SHORT).show();
                }
            });
            holder.pinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mcontext, "评论！", Toast.LENGTH_SHORT).show();
                }
            });*/

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    static class ViewHolder {
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

        @BindView(R.id.practice_info)TextView practice_info;
        @BindView(R.id.username) TextView username;
        @BindView(R.id.user_image)CircleImageView img;
        @BindView(R.id.shareinfo)TextView shareinfo;
        @BindView(R.id.dianzan)TextView dianzan;
        @BindView(R.id.pinglun)TextView pinglun;
        @BindView(R.id.shareimg)ImageView shareimg;
        @BindView(R.id.dianzancount)TextView dianzancount;
        @BindView(R.id.pingluninfo)TextView pingluninfo;

    }

}