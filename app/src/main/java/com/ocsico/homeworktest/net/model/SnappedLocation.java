package com.ocsico.homeworktest.net.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class SnappedLocation {

    public double latitude;
    public double longitude;

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
