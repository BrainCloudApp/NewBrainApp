package com.lmq.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newbrainapp.R;
import com.lmq.ui.entity.AppMessage;
import com.lmq.ui.entity.HealthProblem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class ProblemListAdapter extends RecyclerView.Adapter {


    private static final String TAG = ProblemListAdapter.class.getSimpleName();
    private ArrayList<HealthProblem> source;


    public ProblemListAdapter(ArrayList<HealthProblem> list) {
        this.source = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_health_problemhistory_item, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        try {

            MyHolder holder = (MyHolder) viewHolder;

                holder.time.setText("日期："+source.get(position).getTime());
                holder.name.setText("疾病名称："+source.get(position).getName());
                holder.result.setText("状况："+source.get(position).getStatus());
                 holder.result.setText("治疗效果："+source.get(position).getResult());
                  holder.beizhu.setText("备注："+source.get(position).getBeizhu());


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return source.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        public int position;
        public MyHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

        @BindView(R.id.problemhistory_time) TextView time;
        @BindView(R.id.problemhistory_name) TextView name;
        @BindView(R.id.problemhistory_result) TextView result;
        @BindView(R.id.problemhistory_beizhu) TextView beizhu;

    }

}