package com.example.ehab.medapp.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.models.DayPart;
import com.example.ehab.medapp.models.Drug;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayPartAdapter extends RecyclerView.Adapter<DayPartAdapter.MyViewHolder> {

    private ArrayList<DayPart> dayParts;


    private Activity activity;
    public DayPartAdapter(ArrayList<DayPart> dayParts,Activity activity) {
        this.dayParts = dayParts;
        this.activity = activity;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_part_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DayPart dayPart = dayParts.get(position);
        holder.tvPartName.setText(dayPart.getName());

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false);
        holder.partsRecycler.setLayoutManager(hs_linearLayout);
        holder.partsRecycler.setHasFixedSize(true);
        PartDrugAdapter drugAdapter = new PartDrugAdapter(dayParts.get(position).getDrugs());
        holder.partsRecycler.setAdapter(drugAdapter);
    }

    @Override
    public int getItemCount() {
        return dayParts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day_part_name)
        TextView tvPartName;
        @BindView(R.id.day_part_recyclerView)
        RecyclerView partsRecycler;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}