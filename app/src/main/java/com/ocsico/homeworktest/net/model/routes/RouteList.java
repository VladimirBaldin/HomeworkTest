package com.ocsico.homeworktest.net.model.routes;

import java.util.ArrayList;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class RouteList {
    public ArrayList<Route> routes;

    public Route getShortestRoute() {
        Route shortest = null;
        if (routes != null && routes.size() > 0) {
            shortest = routes.get(0);
            int shortestDistance = shortest.getDistance();
            for (Route route : routes) {
                int distance = route.getDistance();
                if (distance < shortestDistance) {
                    shortest = route;
                    shortestDistance = shortest.getDistance();
                }
            }
        }
        return shortest;
    }
}
