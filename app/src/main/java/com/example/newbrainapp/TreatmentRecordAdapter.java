package com.example.newbrainapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TreatmentRecordAdapter extends RecyclerView.Adapter<TreatmentRecordAdapter.ViewHolder> {

    private List<TreatmentRecordBean> mTreatmentRecordBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView treatPro;
        TextView treatDate;
        TextView treatRes;

        public ViewHolder(View view){
            super(view);
            treatPro = view.findViewById(R.id.treatment_project_name);
            treatDate = view.findViewById(R.id.treatment_date);
            treatRes = view.findViewById(R.id.treatment_result);
        }
    }

    public TreatmentRecordAdapter(List<TreatmentRecordBean> treatmentRecordList){
        mTreatmentRecordBeanList = treatmentRecordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.treatment_record_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreatmentRecordBean treatmentRecord = mTreatmentRecordBeanList.get(position);
        holder.treatPro.setText(treatmentRecord.getTreatmentProject());
        holder.treatDate.setText(treatmentRecord.getTreatmentDate());
        holder.treatRes.setText(treatmentRecord.getTreatmentResult());
    }

    @Override
    public int getItemCount() {
        return mTreatmentRecordBeanList.size();
    }
}
