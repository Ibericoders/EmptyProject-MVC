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
import com.ibericoders.ibinternal.app.activities.records.NewRecordActivity;
import com.ibericoders.ibinternal.app.fragments.generics.InflatedFragment;
import com.ibericoders.ibinternal.content.model.records.Attendee;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class MeetingFragment extends InflatedFragment {


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
    View mView;
    Record mRecord;

    public static MeetingFragment newInstance(Record rec){

        Bundle args = new Bundle();
        args.putParcelable("RECORD", rec);
        MeetingFragment fragment = new MeetingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.f_records_meeting, null);

        inflateView(mView);
        initAttrs();
        fillView(mView);

        return mView;
    }

    @Override
    protected void initAttrs() {

        mRecord = getArguments().getParcelable("RECORD");

    }

    @Override
    protected void fillView(View view) {

        if (mRecord.getDate()!=null) {
            dateSelector.setText(mRecord.getDate().toString());
        }
        if(mRecord.getTitle()!=null){
            topicSelector.setText(mRecord.getTitle());
        }
    }

    @OnClick(R.id.recordsmeeting_date)
    public void showDateSelector(){
        Calendar cal=Calendar.getInstance();
        DatePickerDialog dgDate=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fechaselec=view.getDayOfMonth()+"/"+(view.getMonth()+1)+"/"+view.getYear();
                dateSelector.setText(fechaselec);

                Calendar mCal = Calendar.getInstance();
                mCal.set(year, month, dayOfMonth);
                mRecord.setDate(mCal.getTimeInMillis());
            }
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        dgDate.show();
    }

    public Record getData(){

        //La fecha se guarda automáticamente
        mRecord.setTitle(topicSelector.getText().toString());
        //TODO Añadir los participantes.
        return mRecord;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecord = ((NewRecordActivity)getActivity()).mAdapter.getRecordData();

        if (mView != null){
            fillView(mView);
        }
    }
}


