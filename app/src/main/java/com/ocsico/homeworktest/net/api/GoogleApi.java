package com.ocsico.homeworktest.net.api;

import com.ocsico.homeworktest.net.model.routes.RouteList;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by v.baldin on 02.03.2017.
 */

/**
 * Provides API to calculate routes.
 */
public interface GoogleApi {

    @GET("/maps/api/directions/json")
    RouteList directions(@Query("origin") String origin,
                         @Query("destination") String destination,
                         @Query("key") String key);
}
