package com.ibericoders.ibinternal.app.fragments.records;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.fragments.generics.InflatedFragment;
import com.ibericoders.ibinternal.app.managers.LocationUpdaterManager;

import butterknife.BindView;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class MeetingFragment extends InflatedFragment implements View.OnClickListener{


    /*
     * Atributos de UI
     */
    @BindView(R.id.recordsmeeting_date)
    EditText dateSelector;

    @BindView(R.id.recordsmeeting_hour)
    EditText hourSelector;

    @BindView(R.id.recordsmeeting_location)
    EditText locationSelector;

    @BindView(R.id.recordsmeeting_locationpicker)
    ImageView locationPicker;

    /*
     * Atributos de negocio
     */


    public static MeetingFragment newInstance(){

        return new MeetingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = (View) inflater.inflate(R.layout.f_records_meeting, container);

        inflateView(v);
        initAttrs();
        fillView(v);

        return v;
    }

    @Override
    protected void initAttrs() {

        dateSelector.setOnClickListener(this);
        hourSelector.setOnClickListener(this);
        locationPicker.setOnClickListener(this);
    }

    @Override
    protected void fillView(View view) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.recordsmeeting_date:

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateSelector.setText(day + "/" + month + "/" + year);
                    }
                }, 2017, 9, 29);
                break;

            case R.id.recordsmeeting_hour:

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        hourSelector.setText(hour + ":" + minute);
                    }
                }, 20, 0, true);
                break;

            case R.id.recordsmeeting_location:
                break;
            case R.id.recordsmeeting_locationpicker:
                LocationUpdaterManager lum = new LocationUpdaterManager(getContext());
                lum.getAddressLine(getContext());
                break;
        }
    }
}
