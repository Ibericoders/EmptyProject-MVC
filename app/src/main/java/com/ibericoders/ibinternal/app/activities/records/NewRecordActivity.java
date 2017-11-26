package com.ibericoders.ibinternal.app.activities.records;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.ibericoders.ibinternal.R;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;
import com.ibericoders.ibinternal.app.adapters.records.NewRecordPagerAdapter;
import com.ibericoders.ibinternal.content.model.records.Record;

import butterknife.BindView;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class NewRecordActivity extends InflatedActivity {

    /*
     * Constantes de la actividad
     */


    /*
     * Atributos de la UI
     */
    @BindView(R.id.newrecord_tablayout)
    TabLayout tabs;

    @BindView(R.id.newrecord_fragmentplaceholder)
    ViewPager viewPagerFragments;

    @BindView(R.id.fab_save)
    FloatingActionButton saveData;

    /*
     * Atributos de negocio
     */
    public Record rec;
    public NewRecordPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_newrecord);

        inflateView();
        initAttrs();
        setupToolbar(null);
        fillView();
        initListeners();
    }

    @Override
    protected void initAttrs() {

        mAdapter = new NewRecordPagerAdapter(this.getSupportFragmentManager(), new NewRecordPagerAdapter.OnLastFragmentShowed() {
            @Override
            public void isLastFragment(boolean isLast) {

                Interpolator interpolator = AnimationUtils.loadInterpolator(NewRecordActivity.this,
                        android.R.interpolator.fast_out_slow_in);
                if (isLast){
                    saveData.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setInterpolator(interpolator)
                            .setDuration(500)
                            .start();
                } else {
                    saveData.animate()
                            .scaleX(0f)
                            .scaleY(0f)
                            .setInterpolator(interpolator)
                            .setDuration(300)
                            .start();
                }
            }
        });

    }

    @Override
    protected void fillView() {
        saveData.setScaleX(0f);
        saveData.setScaleY(0f);

        viewPagerFragments.setAdapter(mAdapter);
        tabs.setupWithViewPager(viewPagerFragments);
        viewPagerFragments.addOnPageChangeListener(mAdapter);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setResult(RESULT_CANCELED);
    }
}
