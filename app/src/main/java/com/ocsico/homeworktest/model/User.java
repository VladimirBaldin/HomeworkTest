package com.ocsico.homeworktest.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class User extends RealmObject {

    @PrimaryKey
    public long userid;

    public Owner owner;
    public RealmList<Vehicle> vehicles;
    public long lastModified;

    public boolean isCorrect() {
        return userid > 0 && owner != null;
    }

    public void deleteFromDb() {
        owner.deleteFromRealm();
        vehicles.deleteAllFromRealm();
        deleteFromRealm();
    }

}
