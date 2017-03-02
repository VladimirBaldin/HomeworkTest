package com.ocsico.homeworktest.net.model;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class VehiclePosition {

    public long vehicleid;
    public double lat;
    public double lon;

    public boolean isCorrect() {
        return vehicleid > 0;
    }
}
