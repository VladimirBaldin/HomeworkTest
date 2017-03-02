package com.ocsico.homeworktest.net.requests;

import com.ocsico.homeworktest.net.api.WebApi;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by v.baldin on 02.03.2017.
 */

public abstract class AbsCachingRequest<T> extends RetrofitSpiceRequest<T, WebApi> {

    protected static final long CACHE_EXPIRED_TIMEOUT = 24 * 60 * 60 * 1000;// in milliseconds

    public AbsCachingRequest(Class<T> clazz, Class<WebApi> retrofitedInterfaceClass) {
        super(clazz, retrofitedInterfaceClass);
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        if (!cacheExpired())
            return getDataFromCache();
        T result;
        result = doRequest();
        saveInCache(result);
        return result;
    }

    protected abstract T doRequest();

    protected abstract T getDataFromCache();

    protected abstract boolean cacheExpired();

    protected abstract void saveInCache(T data);
}
