package com.example.newbrainapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecoverTargetAdapter extends RecyclerView.Adapter<RecoverTargetAdapter.ViewHolder>{

    private List<RecoverTargetBean> mRecoverTargetBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recover_time) TextView recoverTime;
        @BindView(R.id.recover_target) TextView recoverTarget;
        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public RecoverTargetAdapter(List<RecoverTargetBean> recoverTargetBeanList){
        mRecoverTargetBeanList = recoverTargetBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assess_recover_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecoverTargetBean recoverTarget = mRecoverTargetBeanList.get(position);
        holder.recoverTime.setText(recoverTarget.getRecoverTime());
        holder.recoverTarget.setText(recoverTarget.getRecoverTarget());
    }

    @Override
    public int getItemCount() {
        return mRecoverTargetBeanList.size();
    }
}
