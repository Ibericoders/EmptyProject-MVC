package com.ibericoders.ibinternal.app.fragments.records;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.records.NewRecordActivity;
import com.ibericoders.ibinternal.app.fragments.generics.InflatedFragment;
import com.ibericoders.ibinternal.content.model.records.Record;

/**
 * Created by Jorge on 26/11/2017.
 */

public class PreviewFragment extends InflatedFragment {

    private Record mRecord;
    private View mView;

    public static PreviewFragment newInstance (Record record){
        Bundle args = new Bundle();
        args.putParcelable("RECORD", record);

        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.f_records_preview, null);

        inflateView(mView);
        initAttrs();
        fillView(mView);

        return mView;
    }

    @Override
    protected void initAttrs() {

    }

    @Override
    protected void fillView(View view) {
        Log.d("RESULT", "CHECK");
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
