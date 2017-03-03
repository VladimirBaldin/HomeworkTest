package com.ocsico.homeworktest.net.model.routes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class Leg {
    public Duration duration;
    public Distance distance;
    public List<Step> steps;

    public List<LatLng> getPoints(){
        List<LatLng> points = new ArrayList<>();
        for (Step step : steps) {
            points.addAll(step.getPoints());
        }
        return points;
    }
}
