package com.example.ehab.medapp;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ehab.medapp.adapters.DaysAdapter;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedsFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {


    @BindView(R.id.list_meds_days)
    RecyclerView recyclerView;
    @BindView(R.id.sp_meds_times_repeat)
    Spinner spinner;
    @BindView(R.id.et_meds_time)
    EditText timeTake;
    @BindView(R.id.rg_meds_schedule)
    RadioGroup schedule;
    @BindView(R.id.rb_meds_sd)
    RadioButton sDay;
    @BindView(R.id.rb_meds_everyd)
    RadioButton eDay;
    private TimePickerDialog tpd;
    private DaysAdapter daysAdapter;
    private  String[] names = null;


    public AddMedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_add_meds, container, false);

        ButterKnife.bind(this,view);
        names = getResources().getStringArray(R.array.weekday);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.reminder_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Toast.makeText(getActivity(), "" + spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
        timeTake.setInputType(InputType.TYPE_NULL);
        timeTake.requestFocus();
        timeTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tpd == null) {


                    tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                            AddMedsFragment.this,
                            DateCal.get(Calendar.HOUR_OF_DAY),
                            DateCal.get(Calendar.MINUTE),
                            false
                    );


                } else {
                    tpd.initialize(
                            AddMedsFragment.this,
                            DateCal.get(Calendar.HOUR_OF_DAY),
                            DateCal.get(Calendar.MINUTE),
                            DateCal.get(Calendar.SECOND),
                            false
                    );
                }

//                tpd.setMinTime(DateCal.get(Calendar.HOUR_OF_DAY),DateCal.get(Calendar.MINUTE),
//                        DateCal.get(Calendar.SECOND));

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                if (!tpd.isAdded()) {
                    tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
                }

            }

        });


        sDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility()==View.GONE)
                    recyclerView.setVisibility(View.VISIBLE);
                daysAdapter =
                        new DaysAdapter(getActivity(), names, new ClickListener() {
                            @Override
                            public void onPositionClicked(int position) {
                                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                            }
                        });
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2,GridLayoutManager.HORIZONTAL, false);

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(daysAdapter);
            }
        });

        eDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility()==View.VISIBLE)
                    recyclerView.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    Calendar DateCal = Calendar.getInstance();
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        DateCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        DateCal.set(Calendar.MINUTE, minute);

        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        timeTake.setText(sdf.format(DateCal.getTime()));
    //    time=sdf.format(DateCal.getTime());

    }
}
