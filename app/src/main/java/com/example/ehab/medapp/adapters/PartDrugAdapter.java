package com.example.ehab.medapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.models.Drug;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartDrugAdapter extends RecyclerView.Adapter<PartDrugAdapter.MyViewHolder> {


    private ArrayList<Drug> drugsList;


    public PartDrugAdapter(ArrayList<Drug> drugsList) {
        this.drugsList = drugsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drug_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        Drug drug = drugsList.get(position);
        holder.tvName.setText(drug.getName());
        holder.tvDescription.setText(drug.getDescription());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("event name=",drugsList.get(position).getName());
            }
        });



    }

    @Override
    public int getItemCount() {
        return drugsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
   

        @BindView(R.id.drug_tv_name)
        TextView tvName;

        @BindView(R.id.drug_tv_descr)
        TextView tvDescription;

        @BindView(R.id.drug_iv_status)
        ImageView ivStatus;
 
        @BindView(R.id.drug_cb)
        CheckBox cbAction;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
