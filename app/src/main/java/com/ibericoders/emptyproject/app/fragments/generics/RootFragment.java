package com.ibericoders.emptyproject.app.fragments.generics;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public abstract class RootFragment extends Fragment {

    protected abstract void initAttrs();

    protected abstract void inflateView(View view);

    protected abstract void fillView(View view);
}
