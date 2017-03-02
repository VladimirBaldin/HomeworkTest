package com.ocsico.homeworktest.net.requests;

import com.ocsico.homeworktest.db.DbHelper;
import com.ocsico.homeworktest.model.User;
import com.ocsico.homeworktest.net.model.UserList;
import com.ocsico.homeworktest.net.api.WebApi;

import java.util.Iterator;
import java.util.List;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class GetUsersRequest extends AbsCachingRequest<UserList> {

    public GetUsersRequest() {
        super(UserList.class, WebApi.class);
    }

    @Override
    protected UserList doRequest() {
        return getService().getUserList();
    }

    @Override
    protected UserList getDataFromCache() {
        UserList data = new UserList();
        data.data = DbHelper.getUsers();
        return data;
    }

    @Override
    protected boolean cacheExpired() {
        UserList data = getDataFromCache();
        if (data != null && data.data != null && data.data.size() > 0) {
            long lastModified = data.data.get(0).lastModified;
            if (System.currentTimeMillis() - lastModified < CACHE_EXPIRED_TIMEOUT)
                return false;
        }
        return true;
    }

    @Override
    protected void saveInCache(UserList data) {
        if (data != null && data.data != null) {
            List<User> users = data.data;
            // remove incorrect data
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                if (!user.isCorrect())
                    iterator.remove();
            }
            DbHelper.saveUsers(users);
        }
    }
}
