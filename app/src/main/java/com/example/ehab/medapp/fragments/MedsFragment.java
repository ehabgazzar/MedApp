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
import com.example.ehab.medapp.adapters.DayPartAdapter;
import com.example.ehab.medapp.adapters.DrugAdapter;
import com.example.ehab.medapp.models.Drug;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedsFragment extends Fragment {
    @BindView(R.id.drugs_recyclerView)
    RecyclerView drugRecycler;
    DrugAdapter event_list_parent_adapter;

    public MedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_meds, container, false);
        ButterKnife.bind(this,view);
        ArrayList<Drug> drugs= new ArrayList<>();
        Drug drug;
//        for(int i = 1 ; i <5;i++)
//        {
//            drug= new Drug(""+i,"Drug "+i,"1","10:30 AM","Short Des Short Des");
//            drugs.add(drug);
//
//        }

        event_list_parent_adapter = new DrugAdapter(drugs,getActivity());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        drugRecycler.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
        drugRecycler.addItemDecoration(dividerItemDecoration);
        drugRecycler.setItemAnimator(new DefaultItemAnimator());
        drugRecycler.setAdapter(event_list_parent_adapter);
        return view;
    }

}
