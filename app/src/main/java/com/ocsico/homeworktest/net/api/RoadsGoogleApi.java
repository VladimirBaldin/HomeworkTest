package com.ocsico.homeworktest.net.api;

import com.ocsico.homeworktest.net.model.SnappedPoints;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by v.baldin on 02.03.2017.
 */

/**
 * Provides API to calculate roads.
 */
public interface RoadsGoogleApi {

    @GET("/v1/snapToRoads")
    SnappedPoints snapToRoads(@Query("path") String path,
                              @Query("interpolate") boolean interpolate,
                              @Query("key") String key);
    // TODO needs other model for result
//    @GET("/maps/api/directions/json")
//    SnappedPoints directions(@Query("origin") String origin,
//                              @Query("destination") String destination,
//                              @Query("key") String key);
}
