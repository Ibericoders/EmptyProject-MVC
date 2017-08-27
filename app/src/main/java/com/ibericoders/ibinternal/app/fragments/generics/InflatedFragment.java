package com.ibericoders.ibinternal.app.fragments.generics;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public abstract class InflatedFragment extends RootFragment {

    @Override
    protected void inflateView(View view) {

        ButterKnife.bind(this,view);
    }
}
