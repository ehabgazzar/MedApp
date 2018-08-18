package com.example.ehab.medapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.models.Drug;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ehab.medapp.Utils.getDayPart;
import static com.example.ehab.medapp.fragments.TimelineFragment.firebaseUser;
import static com.example.ehab.medapp.fragments.TimelineFragment.mDatabase;

public class PartDrugAdapter extends RecyclerView.Adapter<PartDrugAdapter.MyViewHolder> {


    private ArrayList<Drug> drugsList;

    private Context mContext;
    public PartDrugAdapter(ArrayList<Drug> drugsList, Context context) {
        this.drugsList = drugsList;
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_drug_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        Drug drug = drugsList.get(position);
        holder.tvName.setText(drug.getName());
        holder.tvTime.setText(drug.getTime());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("event name=",drugsList.get(position).getName());
            }
        });

        if(drug.isTaken()) {
            holder.cbAction.setChecked(true);
            holder.cbAction.setBackground(mContext.getResources().getDrawable(R.drawable.checked));
            holder.ivStatus.setImageResource(R.drawable.bluedot);

        }
       else {
            holder.cbAction.setChecked(false);
            holder.cbAction.setBackground(mContext.getResources().getDrawable(R.drawable.check));
            holder.ivStatus.setImageResource(R.drawable.blueborderdot);
        }
        holder.cbAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(holder.cbAction.isChecked()) {
                    holder.cbAction.setBackground(mContext.getResources().getDrawable(R.drawable.checked));
                    holder.ivStatus.setImageResource(R.drawable.bluedot);
                    drugsList.get(position).setTaken(true);
                    mDatabase.child("usersDrugs").child(firebaseUser.getUid()).child(drugsList.get(position).getDayPart()).child(drugsList.get(position).getId()).setValue(drugsList.get(position));
                }
                else {
                    holder.cbAction.setBackground(mContext.getResources().getDrawable(R.drawable.check));
                    drugsList.get(position).setTaken(false);
                    holder.ivStatus.setImageResource(R.drawable.blueborderdot);
                    mDatabase.child("usersDrugs").child(firebaseUser.getUid()).child(drugsList.get(position).getDayPart()).child(drugsList.get(position).getId()).setValue(drugsList.get(position));
                }
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

        @BindView(R.id.drug_tv_time)
        TextView tvTime;

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
