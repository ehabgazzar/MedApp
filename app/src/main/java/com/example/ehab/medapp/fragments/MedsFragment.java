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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehab.medapp.R;
import com.example.ehab.medapp.adapters.DayPartAdapter;
import com.example.ehab.medapp.adapters.DrugAdapter;
import com.example.ehab.medapp.models.DayPart;
import com.example.ehab.medapp.models.Drug;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.example.ehab.medapp.fragments.TimelineFragment.LIST_STATE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedsFragment extends Fragment {
    @BindView(R.id.drugs_recyclerView)
    RecyclerView drugRecycler;
    private DrugAdapter event_list_parent_adapter;
    private ArrayList<Drug> drugs;
    Parcelable listState;
    private LinearLayoutManager mLayoutManager;

    public MedsFragment() {
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
//            init();
            mLayoutManager.onRestoreInstanceState(listState);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_meds, container, false);
        ButterKnife.bind(this,view);
        drugs= new ArrayList<>();
        Drug drug;
        Query myTopPostsQuery =  TimelineFragment.mDatabase.child("usersDrugs").child(TimelineFragment.firebaseUser.getUid());
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot1: postSnapshot.getChildren()) {

                        Drug drug= dataSnapshot1.getValue(Drug.class);
                        drug.setId(dataSnapshot1.getKey());
                        drug.setDayPart(postSnapshot.getKey());
                        Log.e("Respomnse", postSnapshot.getKey()+ " /dddd");
                        drugs.add(drug);
                        Log.e("obj res", dataSnapshot1.getKey() + " /llll");


                    }



                }
                event_list_parent_adapter.addItem(drugs);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        });
        event_list_parent_adapter = new DrugAdapter(new ArrayList<Drug>(),getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        drugRecycler.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drugRecycler.getContext(), mLayoutManager.getOrientation());
        drugRecycler.addItemDecoration(dividerItemDecoration);
        drugRecycler.setItemAnimator(new DefaultItemAnimator());
        drugRecycler.setAdapter(event_list_parent_adapter);

        return view;
    }

}
