package com.ibericoders.ibinternal.app.fragments.records;

/**
 * Created by shish on 19/11/2017.
 */
import android.app.DatePickerDialog;
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
import com.ibericoders.ibinternal.content.model.records.Record;

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

    /*
     * Atributos de negocio
     */
    private Record mRecord;
    private View mView;

    public static MemoryFragment newInstance(Record rec) {

        Bundle args = new Bundle();
        args.putParcelable("RECORD", rec);
        MemoryFragment fragment = new MemoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            mView = (View) inflater.inflate(R.layout.f_records_memory, null);
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

        if (mRecord.getAct()!=null) {
            TextoResumen.setText(mRecord.getAct());
        }
        if(mRecord.getNextMeeting()!=null){
            TextoNextDate.setText(mRecord.getNextMeeting().toString());
        }
    }
    @OnClick(R.id.addr_nextDate)
    public void setNextDate(){
        Calendar cal=Calendar.getInstance();
        DatePickerDialog dgDate=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fechaselec=view.getDayOfMonth()+"/"+(view.getMonth()+1)+"/"+view.getYear();
                TextoNextDate.setText(fechaselec);

                Calendar mCal = Calendar.getInstance();
                mCal.set(year, month, dayOfMonth);
                mRecord.setNextMeeting(mCal.getTimeInMillis());

            }
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        mRecord.setNextMeeting(cal.getTimeInMillis());
        dgDate.show();
    }

    public Record getData(){

        //La fecha de la próxima reunión se guarda automáticamente
        mRecord.setAct(TextoResumen.getText().toString());
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
