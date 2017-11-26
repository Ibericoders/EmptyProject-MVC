package com.ibericoders.ibinternal.app.adapters.records;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.ibericoders.ibinternal.app.fragments.generics.InflatedFragment;
import com.ibericoders.ibinternal.app.fragments.records.MeetingFragment;
import com.ibericoders.ibinternal.app.fragments.records.MemoryFragment;
import com.ibericoders.ibinternal.app.fragments.records.PreviewFragment;
import com.ibericoders.ibinternal.content.model.records.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 25/11/2017.
 */

public class NewRecordPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{

    private Record mRecord;
    private List<InflatedFragment> fragments;
    private OnLastFragmentShowed mListener;

    public NewRecordPagerAdapter(FragmentManager fm, OnLastFragmentShowed listener) {
        super(fm);
        mRecord = new Record(null, null, null, null, null);

        fragments = new ArrayList<>();
        fragments.add(MeetingFragment.newInstance(mRecord));
        fragments.add(MemoryFragment.newInstance(mRecord));
        fragments.add(PreviewFragment.newInstance(mRecord));

        this.mListener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Se le llama cuando la página actual empieza a ser desplazada y cuando la nueva se muestra.
        if (position == 0){
            mListener.isLastFragment(false);
            MeetingFragment fragment = (MeetingFragment) fragments.get(position);
            Record rec1 = fragment.getData();

            if (rec1.getDate() != null && rec1.getDate() > 0){
                mRecord.setDate(rec1.getDate());
            }
            if (!TextUtils.isEmpty(rec1.getTitle())){
                mRecord.setTitle(rec1.getTitle());
            }
            if (rec1.getAttendees() != null && rec1.getAttendees().size() > 0){
                mRecord.setAttendees(rec1.getAttendees());
            }

        } else if (position == 1){
            mListener.isLastFragment(false);
            MemoryFragment fragment = (MemoryFragment) fragments.get(position);
            Record rec2 = fragment.getData();

            if (!TextUtils.isEmpty(rec2.getAct())){
                mRecord.setAct(rec2.getAct());
            }
            if (rec2.getNextMeeting() != null && rec2.getNextMeeting() > 0){
                mRecord.setNextMeeting(rec2.getNextMeeting());
            }

        } else if (position == 2){
            mListener.isLastFragment(true);
        }

    }

    @Override
    public void onPageSelected(int position) {
        //Devuelve la nueva página seleccionada
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Se le llama cuando el estado del scroll cambia
        //(Posibles estados: SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING)
    }

    public Record getRecordData(){
        return mRecord;
    }

    public interface OnLastFragmentShowed {

        void isLastFragment(boolean isLast);
    }
}
