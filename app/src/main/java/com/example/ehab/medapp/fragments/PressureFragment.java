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
import android.widget.TextView;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.adapters.MeasurementsAdapter;
import com.example.ehab.medapp.models.Measure;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    Measure measure;
    private ArrayList<Measure> measures;
    private LinearLayoutManager mLayoutManager;
    private Parcelable listState;
    private FirebaseDatabase database;
    TextView toolbarTitle;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private final  String LIST_STATE_KEY = "recycler_list_state";
    private FirebaseAuth mAuth;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pressure, container, false);
        ButterKnife.bind(this,view);
         measures = new ArrayList<>();
        if(mDatabase==null) {
            database = FirebaseDatabase.getInstance();

            mDatabase = database.getReference();
        }
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
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
