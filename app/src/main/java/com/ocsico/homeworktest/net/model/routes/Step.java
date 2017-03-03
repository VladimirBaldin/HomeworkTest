package com.ocsico.homeworktest.net.model.routes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class Step {

    public Distance distance;
    public RouteLocation start_location;
    public RouteLocation end_location;

    public List<LatLng> getPoints(){
        List<LatLng> points = new ArrayList<>();
        points.add(start_location.getLatLng());
        points.add(end_location.getLatLng());
        return points;
    }
}
