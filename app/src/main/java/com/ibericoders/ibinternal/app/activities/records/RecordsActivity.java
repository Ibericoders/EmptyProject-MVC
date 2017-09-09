package com.ibericoders.ibinternal.app.activities.records;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.adapters.records.RecordListAdapter;
import com.ibericoders.ibinternal.app.persistence.records.RecordsDatabase;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.List;

import butterknife.BindView;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class RecordsActivity extends InflatedActivity implements View.OnClickListener{

    /*
     * Constantes de la actividad
     */
    private static final int NEW_RECORD = 10;

    /*
     * Atributos de la UI
     */
    @BindView(R.id.records_list_lv)
    ListView recordsList;

    @BindView(R.id.records_new_fab)
    FloatingActionButton newRecord;

    /*
     * Atributos de negocio
     */
    private RecordListAdapter mListAdapter;
    private RecordsDatabase mDatabase;
    private List<Record> mRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_records);

        initAttrs();
        inflateView();
        setupToolbar(null);
        fillView();
        initListeners();
    }

    @Override
    protected void initAttrs() {

        mDatabase = new RecordsDatabase(this);
        mRecordList = mDatabase.getAllRecords();

        mListAdapter = new RecordListAdapter(this, mRecordList);
    }

    @Override
    protected void fillView() {

        recordsList.setAdapter(mListAdapter);
    }

    @Override
    protected void initListeners() {

        newRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.records_new_fab){

            Intent intentNew = new Intent(this, NewRecordActivity.class);

            startActivityForResult(intentNew, NEW_RECORD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_RECORD && resultCode == RESULT_OK){

            mRecordList = mDatabase.getAllRecords();

            mListAdapter.notifyDataSetChanged();
        }
    }
}
