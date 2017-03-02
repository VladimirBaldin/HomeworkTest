package com.ocsico.homeworktest.net.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class SnappedPoint {

    public SnappedLocation location;
    public int originalIndex;
    public String placeId;

    public LatLng getLatLng() {
        if (location != null)
            return location.getLatLng();
        else
            return null;
    }
}
