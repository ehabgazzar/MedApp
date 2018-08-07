package com.example.ehab.medapp.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.adapters.DayPartAdapter;
import com.example.ehab.medapp.adapters.PartDrugAdapter;
import com.example.ehab.medapp.models.DayPart;
import com.example.ehab.medapp.models.Drug;
import com.example.ehab.medapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment  {

    @BindView(R.id.event_recycler_view_parent)
    RecyclerView event_recycler_view_parent;

    DayPartAdapter event_list_parent_adapter;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    public final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;
    private LinearLayoutManager mLayoutManager;
    private DatabaseReference  userRef;
    private ValueEventListener mPostListener;
    private FirebaseAuth mAuth;

    public TimelineFragment() {
        // Required empty public constructor
    }


    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Save list state
        listState = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, listState);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
//            init();
            mLayoutManager.onRestoreInstanceState(listState);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);


        init();
        return view;
    }

    void init() {
        ArrayList<DayPart> dayParts = new ArrayList<>();
        ArrayList<Drug> drugs = new ArrayList<>();
        Drug drug;
        for (int i = 1; i < 5; i++) {
            if (i % 2 == 0)
                drug = new Drug("" + i, "Drug " + i, "1", "10:30 AM", "Short Des Short Des", true);
            else
                drug = new Drug("" + i, "Drug " + i, "1", "10:30 AM", "Short Des Short Des", false);
            drugs.add(drug);
        }
        DayPart dayPart1 = new DayPart(getResources().getString(R.string.morning), drugs);
        DayPart dayPart2 = new DayPart(getResources().getString(R.string.afterNoon), drugs);
        DayPart dayPart3 = new DayPart(getResources().getString(R.string.evening), drugs);
        DayPart dayPart4 = new DayPart(getResources().getString(R.string.night), drugs);
        dayParts.add(dayPart1);
        dayParts.add(dayPart2);
        dayParts.add(dayPart3);
        dayParts.add(dayPart4);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mDatabase.child("users-drugs").child(firebaseUser.getUid()).child(getResources().getString(R.string.morning)).setValue(drugs);

        event_list_parent_adapter = new DayPartAdapter(dayParts, getActivity());
        event_recycler_view_parent.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        event_recycler_view_parent.setLayoutManager(mLayoutManager);

        event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
        event_recycler_view_parent.setAdapter(event_list_parent_adapter);
    }


}
