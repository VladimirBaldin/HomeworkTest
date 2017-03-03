package com.ocsico.homeworktest.net.model.routes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class Route {
    public List<Leg> legs;

    public List<LatLng> getPoints(){
        List<LatLng> points = new ArrayList<>();
        for (Leg leg : legs) {
            points.addAll(leg.getPoints());
        }
        return points;
    }

    public int getDistance() {
        int distance = 0;
        for (Leg leg : legs) {
            distance += leg.distance.value;
        }
        return distance;
    }
}
