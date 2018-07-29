package com.example.ehab.medapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ehab.medapp.adapters.DayPartAdapter;
import com.example.ehab.medapp.adapters.PartDrugAdapter;
import com.example.ehab.medapp.models.DayPart;
import com.example.ehab.medapp.models.Drug;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment  implements OnDateSelectedListener {

    @BindView(R.id.event_recycler_view_parent)
    RecyclerView event_recycler_view_parent;
    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    DayPartAdapter event_list_parent_adapter;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this,view);


        widget.setOnDateChangedListener(this);

        ArrayList<DayPart> dayParts= new ArrayList<>();
        ArrayList<Drug> drugs= new ArrayList<>();
        Drug drug;
        for(int i = 1 ; i <5;i++)
        {
            if(i%2==0)
              drug= new Drug(""+i,"Drug "+i,"1","10:30 AM","Short Des Short Des",true);
            else
                drug= new Drug(""+i,"Drug "+i,"1","10:30 AM","Short Des Short Des",false);
            drugs.add(drug);
        }
        DayPart dayPart1= new DayPart("Morning",drugs);
        DayPart dayPart2= new DayPart("AfterNoon",drugs);
        DayPart dayPart3= new DayPart("Evening",drugs);
        DayPart dayPart4= new DayPart("Night",drugs);
        dayParts.add(dayPart1);
        dayParts.add(dayPart2);
        dayParts.add(dayPart3);
        dayParts.add(dayPart4);

        event_list_parent_adapter = new DayPartAdapter(dayParts,getActivity());
        event_recycler_view_parent.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        event_recycler_view_parent.setLayoutManager(mLayoutManager);
        event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
        event_recycler_view_parent.setAdapter(event_list_parent_adapter);
        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        Toast.makeText(getActivity(), ""+FORMATTER.format(calendarDay.getDate()), Toast.LENGTH_SHORT).show();
    }

}
