package com.example.ehab.medapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.models.Measure;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MyViewHolder> {


    private ArrayList<Measure> measures;

    private Context mContext;
    public MeasurementsAdapter(ArrayList<Measure> measures, Context context) {
        this.measures = measures;
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pressure_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        Measure pressure = measures.get(position);
        holder.tvComment.setText(pressure.getComment());
        holder.tvDate.setText(pressure.getDate());
        holder.tvTime.setText(pressure.getType());
        holder.tvMeasure.setText(pressure.getMeasure());

    }

    @Override
    public int getItemCount() {
        return measures.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
   

        @BindView(R.id.tv_pressure_comment)
        TextView tvComment;
        @BindView(R.id.tv_pressure_date)
        TextView tvDate;
        @BindView(R.id.tv_pressure_time)
        TextView tvTime;
        @BindView(R.id.tv_pressure_measure)
        TextView tvMeasure;


 


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
