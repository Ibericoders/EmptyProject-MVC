package com.ibericoders.ibinternal.app.fragments.records;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.fragments.generics.InflatedFragment;
import com.ibericoders.ibinternal.content.model.records.Attendee;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.ArrayList;
import java.util.Calendar;

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

    @BindView(R.id.recordsmeeting_topic)
    EditText topicSelector;


    //@BindView(R.id.recordsmeeting_locationpicker)
    //ImageView locationPicker;

    /*
     * Atributos de negocio
     */
    ArrayList<Attendee> atList;
    Attendee att;
    Record rec;

    public static MeetingFragment newInstance(Record rec, Attendee att, ArrayList<Attendee> atList){
        MeetingFragment fragment = new MeetingFragment();
        fragment.att=att;
        fragment.rec=rec;
        fragment.atList=atList;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.f_records_meeting, null);

        inflateView(v);
        initAttrs();
        fillView(v);
        if (rec.getDate()!=null) {
            dateSelector.setText(rec.getDate().toString());
        }
        if(rec.getTitle()!=null){
            topicSelector.setText(rec.getTitle());
        }
        return v;
    }

    @Override
    protected void initAttrs() {

        dateSelector.setOnClickListener(this);

    }

    @Override
    protected void fillView(View view) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.recordsmeeting_date:

                Calendar cal=Calendar.getInstance();
                DatePickerDialog dgDate=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fechaselec=view.getDayOfMonth()+"/"+(view.getMonth()+1)+"/"+view.getYear();
                        dateSelector.setText(fechaselec);
                    }
                }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
                rec.setDate(cal.getTimeInMillis());
                dgDate.show();
                break;

            /*
            case R.id.recordsmeeting_hour:

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        hourSelector.setText(hour + ":" + minute);
                    }
                }, 20, 0, true);
                break;
            */
        }
    }

    public Record DataSender(){
            rec.setTitle(topicSelector.getText().toString());
            rec.setAttendees(atList);
        return rec;
    }
}


