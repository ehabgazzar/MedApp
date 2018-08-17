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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


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

    public static FirebaseAuth mAuth;
    private ArrayList<Drug> drugs;
    private ArrayList<DayPart> dayParts;
    public static FirebaseDatabase database;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference mDatabase;

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
      dayParts = new ArrayList<>();


      if(mDatabase==null) {
          database = FirebaseDatabase.getInstance();
          database.setPersistenceEnabled(true);
          mDatabase = database.getReference();
      }

        mAuth = FirebaseAuth.getInstance();
         firebaseUser = mAuth.getCurrentUser();
     //   mDatabase.child("users-drugs").child(firebaseUser.getUid()).child("morning").push().setValue(drugs);
        Query myTopPostsQuery = mDatabase.child("usersDrugs").child(firebaseUser.getUid());
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    String DayPartName = null;
                    if(postSnapshot.getKey().equals("Evening"))
                        DayPartName=getResources().getString(R.string.evening);
                    else if(postSnapshot.getKey().equals("Morning"))
                        DayPartName=getResources().getString(R.string.morning);
                    else if(postSnapshot.getKey().equals("AfterNoon"))
                        DayPartName=getResources().getString(R.string.afterNoon);
                    else if(postSnapshot.getKey().equals("Night"))
                        DayPartName=getResources().getString(R.string.night);
                    drugs = new ArrayList<>();
                    DayPart dayPart1=null;
                    for (DataSnapshot dataSnapshot1: postSnapshot.getChildren()) {

                        Drug drug= dataSnapshot1.getValue(Drug.class);
                        drug.setId(dataSnapshot1.getKey());
                        drug.setDayPart(postSnapshot.getKey());
                        Log.e("Respomnse", postSnapshot.getKey()+ " /dddd");
                        drugs.add(drug);
                        Log.e("obj res", dataSnapshot1.getKey() + " /llll");
                         dayPart1 = new DayPart(DayPartName, drugs);

                    }
                    dayParts.add(dayPart1);


                }
                event_list_parent_adapter.addItem(dayParts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        });

        event_list_parent_adapter = new DayPartAdapter(new ArrayList<DayPart>(), getActivity());
        event_recycler_view_parent.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        event_recycler_view_parent.setLayoutManager(mLayoutManager);

        event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
        event_recycler_view_parent.setAdapter(event_list_parent_adapter);
    }


}
