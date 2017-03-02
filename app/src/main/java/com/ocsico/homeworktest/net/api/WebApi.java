package com.ocsico.homeworktest.net.api;

import com.ocsico.homeworktest.net.model.UserList;
import com.ocsico.homeworktest.net.model.VehiclePositions;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by v.baldin on 01.03.2017.
 */

/**
 * Main server API class.
 */
public interface WebApi {

    @GET("/api/?op=list")
    UserList getUserList();

    @GET("/api/?op=getlocations")
    VehiclePositions getVehiclePositions(@Query("userid") long userid);
}
