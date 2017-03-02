package com.ocsico.homeworktest.net;

import com.ocsico.homeworktest.net.api.RoadsGoogleApi;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class GoogleRetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "https://roads.googleapis.com";
//    private final static String BASE_URL = "https://maps.googleapis.com"; // TODO Try to use.

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(RoadsGoogleApi.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getServerUrl());
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
