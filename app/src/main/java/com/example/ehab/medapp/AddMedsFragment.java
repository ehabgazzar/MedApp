package com.example.ehab.medapp;


import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ehab.medapp.adapters.DaysAdapter;
import com.example.ehab.medapp.models.Drug;
import com.example.ehab.medapp.models.Search;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ehab.medapp.Utils.getDayPart;


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
    @BindView(R.id.et_meds_dose)
    EditText etDose;
    @BindView(R.id.et_meds_search)
    AutoCompleteTextView etName;
    @BindView(R.id.rg_meds_schedule)
    RadioGroup rgSchedule;
    @BindView(R.id.rb_meds_sd)
    RadioButton rbSDay;
    @BindView(R.id.rb_meds_everyd)
    RadioButton rbEDay;

    @BindView(R.id.bt_meds_add)
    Button Add;
    private TimePickerDialog tpd;
    private DaysAdapter daysAdapter;
    private  String[] names = null;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private LocalTime time;
    ArrayAdapter<String> searchAdapter;
    public AddMedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_add_meds, container, false);

        ButterKnife.bind(this,view);
         database = FirebaseDatabase.getInstance();
         mDatabase = database.getReference();


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new FetchMoiveTask().execute(etName.getText().toString()+"%");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        names = getResources().getStringArray(R.array.weekday);
        

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.reminder_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Toast.makeText(getActivity(), "" + spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
        timeTake.setInputType(InputType.TYPE_NULL);
//        timeTake.requestFocus();

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


        rbSDay.setOnClickListener(new View.OnClickListener() {
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

        rbEDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility()==View.VISIBLE)
                    recyclerView.setVisibility(View.GONE);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateForm()) {
                    return;
                }
                Drug drug=null;
                if(rbSDay.isChecked()) {
                 ArrayList<String> checked= daysAdapter.getChecked();
                 if(checked.size()>0) {

                      drug= new Drug(etName.getText().toString(),etDose.getText().toString(),timeTake.getText().toString(),
                             "Specific days",false,checked);
                 }
                 else
                 {
                     Toast.makeText(getActivity(), "select Day", Toast.LENGTH_SHORT).show();
                 }
                }
                else {
                     drug= new Drug(etName.getText().toString(),etDose.getText().toString(),timeTake.getText().toString(),
                            "Every day",false);
                }
                mDatabase.child("usersDrugs").child(firebaseUser.getUid()).child(getDayPart(time)).push().setValue(drug).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!

                        Toast.makeText(getActivity(), "Drug Add Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();


                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed

                                Toast.makeText(getActivity(), "Failed To Add New Drug", Toast.LENGTH_SHORT).show();
                            }
                        });


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
        time = new LocalTime(hourOfDay, minute, second);
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        timeTake.setText(sdf.format(DateCal.getTime()));
    //    time=sdf.format(DateCal.getType());

    }

    private boolean validateForm() {
        boolean valid = true;




        String time = timeTake.getText().toString();
        if (TextUtils.isEmpty(time)) {
            timeTake.setError("Required.");
            valid = false;
        } else {
            timeTake.setError(null);
        }


        String dose = etDose.getText().toString();
        if (TextUtils.isEmpty(dose)) {
            etDose.setError("Required.");
            valid = false;
        } else {
            etDose.setError(null);
        }


        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etName.setError("Required.");
            valid = false;
        } else {
            etName.setError(null);
        }

        return valid;
    }



        public class FetchMoiveTask extends AsyncTask<String, Void,List<Search>> {

            private final String LOG_TAG = FetchMoiveTask.class.getSimpleName();


            private List<Search> getWeatherDataFromJson(String forecastJsonStr)
            {



                Type listType = new TypeToken<ArrayList<Search>>(){}.getType();
                List<Search> results = new Gson().fromJson(forecastJsonStr, listType);

                return results;

            }
            @Override
            protected List<Search> doInBackground(String... params) {



                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                String forecastJsonStr = null;

                try {
                    final String BASE_URL = "http://ehab.raqedu.com/search.php?";

                    final String KEY_PARAM = "q";

                    Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                            .appendQueryParameter(KEY_PARAM, params[0])
                            .build();

                    URL url = new URL(builtUri.toString());


                    // Create the request to Server DB, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    forecastJsonStr = buffer.toString();
                    Log.v(LOG_TAG, "Json Strings" + forecastJsonStr);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }


                    return getWeatherDataFromJson(forecastJsonStr);


            }

            @Override
            protected void onPostExecute(List<Search> result) {
                //super.onPostExecute(strings);




                if (result != null) {
                    String[] COUNTRIES = new String[result.size()];
                    for(int i=0; i <result.size();i++)
                    {
                        COUNTRIES[i]=result.get(i).getName();
                    }
                    searchAdapter = new ArrayAdapter<String>(AddMedsFragment.this.getContext(),
                            android.R.layout.select_dialog_item, COUNTRIES);
                    etName.setThreshold(1);
                    etName.setAdapter(searchAdapter);
                    // New data is back from the server.  Hooray!
                }


            }

        }


}
