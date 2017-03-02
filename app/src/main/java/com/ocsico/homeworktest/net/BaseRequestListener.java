package com.ocsico.homeworktest.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ocsico.homeworktest.R;
import com.ocsico.homeworktest.util.Utils;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by v.baldin on 02.03.2017.
 */
/*
* Base class for request listeners handling errors (returned by RoboSpice).
*/
public abstract class BaseRequestListener<T> implements RequestListener<T> {

    private Context mContext;

    public BaseRequestListener(Context context) {
        mContext = context;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(mContext, Utils.getExceptionMessage(mContext, spiceException), Toast.LENGTH_LONG).show();
    }
}
