package com.ibericoders.ibinternal.app.activities.records;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.FrameLayout;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.fragments.records.MeetingFragment;
import com.ibericoders.ibinternal.app.fragments.records.MemoryFragment;
import com.ibericoders.ibinternal.content.model.records.Attendee;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class NewRecordActivity extends InflatedActivity implements TabLayout.OnTabSelectedListener{

    /*
     * Constantes de la actividad
     */


    /*
     * Atributos de la UI
     */
    @BindView(R.id.newrecord_tablayout)
    TabLayout tabs;

    @BindView(R.id.newrecord_fragmentplaceholder)
    FrameLayout fragmentContainer;

    MeetingFragment meetingFragment;

    /*
     * Atributos de negocio
     */
    ArrayList<Attendee> atList=new ArrayList<>();
    Attendee att=new Attendee(null,null,null);
    public Record rec=new Record(null,null,null,null,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_newrecord);

        initAttrs();
        inflateView();
        setupToolbar(null);
        fillView();
        initListeners();

        //activamos el fragment 0 por defecto
        MeetingFragment meetingFragment = MeetingFragment.newInstance(rec,att,atList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.newrecord_fragmentplaceholder, meetingFragment)
                .commit();
    }

    @Override
    protected void initAttrs() {

    }

    @Override
    protected void fillView() {

    }

    @Override
    protected void initListeners() {

        tabs.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        //¿Qué hacer cuando una tab se selecciona?

        switch(tabs.getSelectedTabPosition()){

            case 0:

                meetingFragment = MeetingFragment.newInstance(rec,att,atList);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.newrecord_fragmentplaceholder, meetingFragment)
                        .commit();
                break;

            case 1:
                MeetingFragment meetingFragment2 = MeetingFragment.newInstance(rec,att,atList);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.newrecord_fragmentplaceholder, meetingFragment2)
                        .commit();
                break;

            case 2:
                MeetingFragment meetingFragment3 = MeetingFragment.newInstance(rec,att,atList);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.newrecord_fragmentplaceholder, meetingFragment3)
                        .commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        switch(tab.getPosition()){

            case 0:

                rec.setTitle(meetingFragment.DataSender().getTitle());
                rec.setDate(meetingFragment.DataSender().getDate());
                rec.setAttendees(meetingFragment.DataSender().getAttendees());

                break;

            case 1:
                MemoryFragment mem=new MemoryFragment();
                rec.setAct(mem.dataSender().getAct());
                rec.setNextMeeting(mem.dataSender().getNextMeeting());
                break;

            case 2:

                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        //¿Qué hacer cuando una tab que esta seleccionada vuelve a seleccionarse?
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setResult(RESULT_CANCELED);
    }
}
