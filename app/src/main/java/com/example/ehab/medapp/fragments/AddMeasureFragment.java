package com.example.ehab.medapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ehab.medapp.AddMedsFragment;
import com.example.ehab.medapp.R;
import com.example.ehab.medapp.models.Measure;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddMeasureFragment extends Fragment  implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.rg_measure)
    RadioGroup rgMeasure;
    @BindView(R.id.rb_add_measure_blood)
    RadioButton rbBlood;
    @BindView(R.id.rb_add_measure_weight)
    RadioButton rbWeight;
    @BindView(R.id.et_measure_date)
    EditText etDate;
    @BindView(R.id.et_add_measure_comment)
    EditText etComment;
    @BindView(R.id.et_add_measure_val)
    EditText etMeasureValue;
    @BindView(R.id.bt_add_measure)
    Button btAdd;
    private DatePickerDialog tpd;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Measure measure;

    public AddMeasureFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_add_measure, container, false);
        ButterKnife.bind(this,view);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tpd == null) {


                    tpd = DatePickerDialog.newInstance(  AddMeasureFragment.this,DateCal.get(Calendar.YEAR),
                            DateCal.get(Calendar.MONTH),
                            DateCal.get(Calendar.DAY_OF_MONTH));


                } else {
                    tpd.initialize(
                            AddMeasureFragment.this,
                            DateCal.get(Calendar.YEAR),
                            DateCal.get(Calendar.MONTH),
                            DateCal.get(Calendar.DAY_OF_MONTH)

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

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                // get selected radio button from radioGroup
                int selectedId = rgMeasure.getCheckedRadioButtonId();

                // find the radiobutton by returned id

                if(selectedId==-1) {
                    Toast.makeText(getActivity(),
                             R.string.err_enter_measure_type, Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                String type = null;
             if(rbBlood.isChecked())
                 measure= new Measure("Blood Pressure",etMeasureValue.getText().toString(),etComment.getText().toString()
                        ,etDate.getText().toString());
             else if(rbWeight.isChecked())
                measure= new Measure("Weight",etMeasureValue.getText().toString(),etComment.getText().toString()
                        ,etDate.getText().toString());



                mDatabase.child("usersMeasures").child(firebaseUser.getUid()).push().setValue(measure).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Measure Added Successfully", Toast.LENGTH_SHORT).show();
                      //  getActivity().onBackPressed();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed To Add New Measure", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;
    }

    Calendar DateCal = Calendar.getInstance();
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DateCal.set(Calendar.YEAR, year);
        DateCal.set(Calendar.MONTH, monthOfYear);
        DateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        etDate.setText(sdf.format(DateCal.getTime()));
    }


    private boolean validateForm() {
        boolean valid = true;




        String time = etMeasureValue.getText().toString();
        if (TextUtils.isEmpty(time)) {
            etMeasureValue.setError("Required.");
            valid = false;
        } else {
            etMeasureValue.setError(null);
        }




        String name = etDate.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etDate.setError("Required.");
            valid = false;
        } else {
            etDate.setError(null);
        }

        return valid;
    }

}
