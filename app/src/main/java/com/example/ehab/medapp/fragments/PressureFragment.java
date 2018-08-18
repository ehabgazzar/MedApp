package com.example.ehab.medapp.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import static com.example.ehab.medapp.fragments.TimelineFragment.LIST_STATE_KEY;
import static com.example.ehab.medapp.fragments.TimelineFragment.firebaseUser;
import static com.example.ehab.medapp.fragments.TimelineFragment.mDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class PressureFragment extends Fragment {
    @BindView(R.id.pressure_recyclerView)
    RecyclerView drugRecycler;
    MeasurementsAdapter event_list_parent_adapter;
    Measure measure;
    private ArrayList<Measure> measures;
    private LinearLayoutManager mLayoutManager;
    private Parcelable listState;

    public PressureFragment() {
        // Required empty public constructor
    }


    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Save list state
        listState = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retrieve list state and list/item positions
        if (savedInstanceState != null) {

            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (listState != null) {
            mLayoutManager.onRestoreInstanceState(listState);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pressure, container, false);
        ButterKnife.bind(this,view);
         measures = new ArrayList<>();

        Query myTopPostsQuery = mDatabase.child("usersMeasures").child(firebaseUser.getUid()).child("Blood Pressure");
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    measure = dataSnapshot1.getValue(Measure.class);

                    measures.add(measure);
                }
                event_list_parent_adapter = new MeasurementsAdapter(measures,getActivity(),"mmHg");

                 mLayoutManager = new LinearLayoutManager(getActivity());
                drugRecycler.setLayoutManager(mLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
                drugRecycler.addItemDecoration(dividerItemDecoration);
                drugRecycler.setItemAnimator(new DefaultItemAnimator());
                drugRecycler.setAdapter(event_list_parent_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

}
