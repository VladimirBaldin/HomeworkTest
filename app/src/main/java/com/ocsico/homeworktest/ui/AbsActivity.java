package com.ocsico.homeworktest.ui;

import android.support.v4.app.FragmentActivity;

import com.ocsico.homeworktest.net.HWRetrofitSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by v.baldin on 01.03.2017.
 */

public abstract class AbsActivity extends FragmentActivity {

    private SpiceManager mSpiceManager = new SpiceManager(HWRetrofitSpiceService.class);

    @Override
    protected void onStart() {
        mSpiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
