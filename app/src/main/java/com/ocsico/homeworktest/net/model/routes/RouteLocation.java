package com.ocsico.homeworktest.net.model.routes;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class RouteLocation {
    public double lat;
    public double lng;

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
