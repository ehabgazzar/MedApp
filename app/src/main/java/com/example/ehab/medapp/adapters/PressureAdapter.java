package com.example.ehab.medapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.models.BloodPressure;
import com.example.ehab.medapp.models.Drug;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PressureAdapter extends RecyclerView.Adapter<PressureAdapter.MyViewHolder> {


    private ArrayList<BloodPressure> bloodPressures;

    private Context mContext;
    public PressureAdapter(ArrayList<BloodPressure> bloodPressures, Context context) {
        this.bloodPressures = bloodPressures;
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
        BloodPressure pressure = bloodPressures.get(position);
        holder.tvComment.setText(pressure.getComment());
        holder.tvDate.setText(pressure.getDate());
        holder.tvTime.setText(pressure.getTime());
        holder.tvMeasure.setText(pressure.getMeasure());

    }

    @Override
    public int getItemCount() {
        return bloodPressures.size();
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
