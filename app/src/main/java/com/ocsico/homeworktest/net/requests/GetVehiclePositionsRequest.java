package com.ocsico.homeworktest.net.requests;

import com.ocsico.homeworktest.net.model.VehiclePositions;
import com.ocsico.homeworktest.net.api.WebApi;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class GetVehiclePositionsRequest extends RetrofitSpiceRequest<VehiclePositions, WebApi> {

    private long mUserId;

    public GetVehiclePositionsRequest(long userId) {
        super(VehiclePositions.class, WebApi.class);
        mUserId = userId;
    }

    @Override
    public VehiclePositions loadDataFromNetwork() throws Exception {
        return getService().getVehiclePositions(mUserId);
    }
}
