package com.lesia.android.vkphotos.rest_utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by lesia on 7/9/15.
 */
public class RestClient
{
    private static final String BASE_URL = "https://api.vk.com/method";
    private IVkApi apiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(IVkApi.class);
    }

    public IVkApi getApiService()
    {
        return apiService;
    }
}
