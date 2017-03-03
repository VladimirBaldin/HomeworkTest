package com.ocsico.homeworktest.net.model.routes;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.ocsico.homeworktest.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class Step {

    public Distance distance;
    public RouteLocation start_location;
    public RouteLocation end_location;
    public Polyline polyline;

    public List<LatLng> getPoints(){
        return PolyUtil.decode(polyline.points);
    }
}
