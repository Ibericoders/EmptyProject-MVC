package com.ibericoders.ibinternal.app.activities.records;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.FrameLayout;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.fragments.records.MeetingFragment;

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

    /*
     * Atributos de negocio
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_newrecord);

        initAttrs();
        inflateView();
        setupToolbar(null);
        fillView();
        initListeners();
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

                MeetingFragment meetingFragment = MeetingFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.newrecord_fragmentplaceholder, meetingFragment)
                        .commit();
                break;

            case 1:
                break;

            case 2:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        //¿Qué hacer cuando una tab deja de estar seleccionada?
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
