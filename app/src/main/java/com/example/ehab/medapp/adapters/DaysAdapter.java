package com.example.ehab.medapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.ehab.medapp.ClickListener;
import com.example.ehab.medapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    String[] names;
    private  String[] namesEN = null;
    ArrayList<String> daysCheck=new ArrayList<>();
    private  Context context;
    private  LayoutInflater layoutInflater;


    public DaysAdapter(Context context, String[] names,ClickListener listener) {
        this.context = context;
        this.names = names;
        layoutInflater = (LayoutInflater.from(context));
        namesEN=context.getResources().getStringArray(R.array.weekdayen);

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.day_item, parent, false);
        DaysAdapter.ViewHolder viewHolder = new DaysAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ViewHolder hold=holder;
        holder.simpleCheckedTextView.setText(names[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hold.simpleCheckedTextView.isChecked()) {
// set cheek mark drawable and set checked property to false
                 //   value = "un-Checked";
                    daysCheck.remove(namesEN[position]);
                    hold.simpleCheckedTextView.setChecked(false);
                    hold.cardView.setBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
                } else {
// set cheek mark drawable and set checked property to true
                  //  value = "Checked";
                    daysCheck.add(namesEN[position]);
                    hold.simpleCheckedTextView.setChecked(true);
                    hold.cardView.setBackgroundColor(context.getResources().getColor(R.color.primary));
                }


            }
        });
    }

    public ArrayList<String> getChecked() {
        return daysCheck;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return names.length;
    }




    /*@Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.day_item, null);

// perform on Click Event Listener on CheckedTextView
       *//* simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    value = "un-Checked";
                    daysCheck.put(names[position],)
                    simpleCheckedTextView.setCheckMarkDrawable(0);
                    simpleCheckedTextView.setChecked(false);
                } else {
// set cheek mark drawable and set checked property to true
                    value = "Checked";
                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.checked);
                    simpleCheckedTextView.setChecked(true);
                }
                Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
            }
        });*//*
        return view;
    }

*/

    public static class ViewHolder extends RecyclerView.ViewHolder  {

         CheckedTextView simpleCheckedTextView ;
         CardView cardView;


        public ViewHolder(View view) {
            super(view);
            cardView= view.findViewById(R.id.day_card);
            simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.tv_day_check);

        }


    }
}
