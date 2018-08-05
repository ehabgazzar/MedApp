package com.example.ehab.medapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.dacer.androidcharts.LineView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment {

    @BindView(R.id.weight_recyclerView)
    RecyclerView drugRecycler;
    MeasurementsAdapter event_list_parent_adapter;

    public WeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_weight, container, false);
        final LineView lineView = (LineView) view.findViewById(R.id.line_view);
        initLineView(lineView);
        ButterKnife.bind(this,view);
        ArrayList<Measure> drugs= new ArrayList<>();
        Measure drug;
        for(int i = 1 ; i <5;i++)
        {
            drug= new Measure("25/8/2018",i+":30 PM ",i+" KG","Any comment Any comment Any comment");
            drugs.add(drug);

        }

        event_list_parent_adapter = new MeasurementsAdapter(drugs,getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        drugRecycler.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
        drugRecycler.addItemDecoration(dividerItemDecoration);
        drugRecycler.setItemAnimator(new DefaultItemAnimator());
        drugRecycler.setAdapter(event_list_parent_adapter);
        return view;
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[] {
                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

        ArrayList<Integer> measurements = new ArrayList<>();
        float random = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < 10; i++) {
            measurements.add((int) (Math.random() * random));
        }



        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(measurements);
//        dataLists.add(dataList2);

        ArrayList<String> dates = new ArrayList<>();
        dates.add("1/7/2018");
        dates.add("2/7/2018");
        dates.add("3/7/2018");
        dates.add("4/7/2018");
        dates.add("5/7/2018");
        dates.add("6/7/2018");
        dates.add("2/7/2018");
        dates.add("3/7/2018");
        dates.add("4/7/2018");
        dates.add("5/7/2018");
        dates.add("6/7/2018");
        lineView.setBottomTextList(dates);
        lineView.setDataList(dataLists);
    }
}
