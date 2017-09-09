package com.ibericoders.ibinternal.app.adapters.records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.common.utils.Utils;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.List;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class RecordListAdapter  extends BaseAdapter{

    private List<Record> recordsList;

    private LayoutInflater mLayoutInflater;

    private Context mContext;

    public RecordListAdapter (Context context, List<Record> records){

        this.mContext = context;
        this.recordsList = records;

        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return recordsList.size();
    }

    @Override
    public Object getItem(int position) {

        return recordsList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = mLayoutInflater.inflate(R.layout.row_recordslist, null);

        TextView tvTitulo = (TextView) view.findViewById(R.id.recordslist_title_tv);
        tvTitulo.setText(recordsList.get(position).getTitle());

        TextView tvFecha = (TextView) view.findViewById(R.id.recordslist_date_tv);
        tvFecha.setText(Utils.getDateString(recordsList.get(position).getDate(), Utils.DATE_FORMAT_ddMM));

        return view;
    }
}
