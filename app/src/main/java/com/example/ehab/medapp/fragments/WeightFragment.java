package com.example.ehab.medapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ehab.medapp.R;
import com.example.ehab.medapp.adapters.MeasurementsAdapter;
import com.example.ehab.medapp.models.Measure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.dacer.androidcharts.LineView;

import static com.example.ehab.medapp.fragments.TimelineFragment.firebaseUser;
import static com.example.ehab.medapp.fragments.TimelineFragment.mDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment {

    @BindView(R.id.weight_recyclerView)
    RecyclerView drugRecycler;
    MeasurementsAdapter event_list_parent_adapter;
    ArrayList<Measure> weights;
    Measure weight;
    public WeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_weight, container, false);
        final LineView lineView = (LineView) view.findViewById(R.id.line_view);

        ButterKnife.bind(this,view);
        weights  = new ArrayList<>();

        Query myTopPostsQuery = mDatabase.child("usersMeasures").child(firebaseUser.getUid()).child("Weight");
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    weight = dataSnapshot1.getValue(Measure.class);
                    weights.add(weight);
                }
                event_list_parent_adapter = new MeasurementsAdapter(weights,getActivity(),"KG");

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                drugRecycler.setLayoutManager(mLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
                drugRecycler.addItemDecoration(dividerItemDecoration);
                drugRecycler.setItemAnimator(new DefaultItemAnimator());
                drugRecycler.setAdapter(event_list_parent_adapter);
                initLineView(lineView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < weights.size(); i++) {
            test.add(weights.get(i).getDate());
        }

        lineView.setColorArray(new int[] {
                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

        ArrayList<Integer> measurements = new ArrayList<>();

        for (int i = 0; i < weights.size(); i++) {
            measurements.add(Integer.valueOf(weights.get(i).getMeasure()));
        }



        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(measurements);
//        dataLists.add(dataList2);

        ArrayList<String> dates = new ArrayList<>();

        lineView.setBottomTextList(test);
        lineView.setDataList(dataLists);
    }
}
