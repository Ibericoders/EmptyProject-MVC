package com.ibericoders.emptyproject.app.activities;

import android.os.Bundle;

import com.ibericoders.emptyproject.app.activities.generics.InflatedActivity;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class MainActivity extends InflatedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
