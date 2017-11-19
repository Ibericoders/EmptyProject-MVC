package com.ibericoders.ibinternal.app.fragments.records;

/**
 * Created by shish on 19/11/2017.
 */
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
import butterknife.OnClick;

public class MemoryFragment extends InflatedFragment {

    /*
     * Atributos de UI
     */
    @BindView(R.id.addr_edittext)
    EditText TextoResumen;
    @BindView(R.id.addr_nextDate)
    EditText TextoNextDate;



    //@BindView(R.id.recordsmeeting_locationpicker)
    //ImageView locationPicker;

    /*
     * Atributos de negocio
     */
    ArrayList<Attendee> atList;
    Attendee att;
    Record rec;

    public static MemoryFragment newInstance(Record rec, Attendee att, ArrayList<Attendee> atList) {
        MemoryFragment fragment = new MemoryFragment();
        fragment.att = att;
        fragment.rec = rec;
        fragment.atList = atList;

        return fragment;
    }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = (View) inflater.inflate(R.layout.f_records_memory, null);
            Context ctx=getContext();
            inflateView(v);
            initAttrs();
            fillView(v);
            if (rec.getAct()!=null) {
                TextoResumen.setText(rec.getAct());
            }
            if(rec.getNextMeeting()!=null){
                TextoNextDate.setText(rec.getNextMeeting().toString());
            }


            return v;

    }


    @Override
    protected void initAttrs() {

    }

    @Override
    protected void fillView(View view) {

    }
    @OnClick(R.id.addr_nextDate)
    public void setNextDate(View view){
        Calendar cal=Calendar.getInstance();
        DatePickerDialog dgDate=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fechaselec=view.getDayOfMonth()+"/"+(view.getMonth()+1)+"/"+view.getYear();
                TextoNextDate.setText(fechaselec);
            }
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        rec.setNextMeeting(cal.getTimeInMillis());
        dgDate.show();
    }
    public Record dataSender(){
        rec.setAct(TextoResumen.getText().toString());
        return rec;
    }
}
