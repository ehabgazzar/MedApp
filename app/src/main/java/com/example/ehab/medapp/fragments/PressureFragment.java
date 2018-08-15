package com.example.ehab.medapp.fragments;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class PressureFragment extends Fragment {
    @BindView(R.id.pressure_recyclerView)
    RecyclerView drugRecycler;
    MeasurementsAdapter event_list_parent_adapter;

    public PressureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pressure, container, false);
        ButterKnife.bind(this,view);
        ArrayList<Measure> drugs= new ArrayList<>();
        Measure drug;
//        for(int i = 1 ; i <5;i++)
//        {
//            drug= new Measure("25/8/2018",i+":30 PM ","120 / 80","Any comment Any comment Any comment");
//            drugs.add(drug);
//
//        }

        event_list_parent_adapter = new MeasurementsAdapter(drugs,getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        drugRecycler.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
        drugRecycler.addItemDecoration(dividerItemDecoration);
        drugRecycler.setItemAnimator(new DefaultItemAnimator());
        drugRecycler.setAdapter(event_list_parent_adapter);

        return view;
    }

}
