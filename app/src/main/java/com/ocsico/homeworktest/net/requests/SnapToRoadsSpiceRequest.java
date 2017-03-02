package com.ocsico.homeworktest.net.requests;

import com.ocsico.homeworktest.net.model.SnappedPoints;
import com.ocsico.homeworktest.net.api.RoadsGoogleApi;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class SnapToRoadsSpiceRequest extends RetrofitSpiceRequest<SnappedPoints, RoadsGoogleApi> {

    private String mPath;
    private boolean mInterpolate;

    public SnapToRoadsSpiceRequest(String path, boolean interpolate) {
        super(SnappedPoints.class,RoadsGoogleApi.class);
        this.mPath = path;
        this.mInterpolate = interpolate;
    }

    @Override
    public SnappedPoints loadDataFromNetwork() throws Exception {
        return getService().snapToRoads(mPath, mInterpolate, "AIzaSyDmYprgqpiY74EtVB0iwqh9kZ51pzNJONk");
    }
}
