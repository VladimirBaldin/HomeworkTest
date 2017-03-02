package com.ocsico.homeworktest.model;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class Vehicle extends RealmObject {

    @PrimaryKey
    public long vehicleid;

    public String make;
    public String model;
    public String year;
    public String color;
    public String vin;
    public String foto;
    public double lat;
    public double lon;

    public String getName() {
        return this.make + " " + this.model;
    }

    public int getColor() {
        int result = Color.TRANSPARENT;
        try {
            result = Color.parseColor(this.color);
        } catch (Exception e) {

        }
        return result;
    }

    public LatLng getLocation() {
        return new LatLng(lat, lon);
    }
}
