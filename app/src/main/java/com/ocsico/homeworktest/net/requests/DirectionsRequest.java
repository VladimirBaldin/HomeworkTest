package com.ocsico.homeworktest.net.requests;

import com.ocsico.homeworktest.net.api.GoogleApi;
import com.ocsico.homeworktest.net.model.routes.RouteList;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class DirectionsRequest extends RetrofitSpiceRequest<RouteList, GoogleApi> {

    private String mOrigin;
    private String mDestination;

    public DirectionsRequest(String origin, String destination) {
        super(RouteList.class,GoogleApi.class);
        mOrigin = origin;
        mDestination = destination;
    }

    @Override
    public RouteList loadDataFromNetwork() throws Exception {
        return getService().directions(mOrigin, mDestination, "AIzaSyAAtsUmNWvu09y88UdPFAhVKGiJTDJQm4k");
    }
}
