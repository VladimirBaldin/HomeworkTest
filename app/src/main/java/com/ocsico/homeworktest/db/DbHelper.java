package com.ocsico.homeworktest.db;

import com.ocsico.homeworktest.model.User;
import com.ocsico.homeworktest.model.Vehicle;
import com.ocsico.homeworktest.net.model.VehiclePosition;

import java.util.List;

import io.realm.Realm;

/**
 * Created by v.baldin on 01.03.2017.
 */

/**
 * Class to handle database operations.
 */
public class DbHelper {

    public static void saveUsers(final List<User> users) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm r) {
                long time = System.currentTimeMillis();
                for (User user : users) {
                    user.lastModified = time;
                    r.copyToRealmOrUpdate(user);
                }
            }
        });
    }

    public static void updateVehicle(final VehiclePosition position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm r) {
                Vehicle vehicle = r.where(Vehicle.class).equalTo("vehicleid", position.vehicleid).findFirst();
                vehicle.lat = position.lat;
                vehicle.lon = position.lon;
            }
        });
    }

    public static List<Vehicle> getVehicles(long userId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).equalTo("userid", userId).findFirst().vehicles;
    }

    public static List<User> getUsers() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).findAll();
    }

}
