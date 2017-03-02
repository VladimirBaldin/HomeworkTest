package com.ocsico.homeworktest.net;

import com.google.gson.Gson;
import com.ocsico.homeworktest.net.api.WebApi;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class HWRetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://mobi.connectedcar360.net";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(WebApi.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        GsonConverter converter = new GsonConverter(new Gson());
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        return new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getServerUrl())
                .setConverter(converter);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

}
