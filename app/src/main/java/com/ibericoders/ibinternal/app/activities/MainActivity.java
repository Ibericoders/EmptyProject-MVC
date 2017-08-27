package com.ibericoders.ibinternal.app.activities;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.ibericoders.ibinternal.app.activities.generics.InflatedActivity;

import io.fabric.sdk.android.Fabric;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class MainActivity extends InflatedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        initAttrs();
        inflateView();
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

    }
}
