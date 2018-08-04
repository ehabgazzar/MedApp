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
import com.example.ehab.medapp.adapters.DrugAdapter;
import com.example.ehab.medapp.adapters.PressureAdapter;
import com.example.ehab.medapp.models.BloodPressure;
import com.example.ehab.medapp.models.Drug;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PressureFragment extends Fragment {
    @BindView(R.id.pressure_recyclerView)
    RecyclerView drugRecycler;
    PressureAdapter event_list_parent_adapter;

    public PressureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pressure, container, false);
        ButterKnife.bind(this,view);
        ArrayList<BloodPressure> drugs= new ArrayList<>();
        BloodPressure drug;
        for(int i = 1 ; i <5;i++)
        {
            drug= new BloodPressure("25/8/2018",i+":30 PM ","120 / 80","Any comment Any comment Any comment");
            drugs.add(drug);

        }

        event_list_parent_adapter = new PressureAdapter(drugs,getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        drugRecycler.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
        drugRecycler.addItemDecoration(dividerItemDecoration);
        drugRecycler.setItemAnimator(new DefaultItemAnimator());
        drugRecycler.setAdapter(event_list_parent_adapter);

        return view;
    }

}
