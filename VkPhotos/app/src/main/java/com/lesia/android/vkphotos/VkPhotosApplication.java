package com.lesia.android.vkphotos;

import android.app.Application;
import android.util.Log;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lesia on 7/9/15.
 */
public class VkPhotosApplication extends Application
{
    private IVkApi vkApi;

    @Override
    public void onCreate() {
        super.onCreate();
       // IVkApi vkApi = ;
        EventBus.getDefault().register(this);
    }

    public void onEvent(String access_token)
    {
        new RestClient().getApiService().getFriendsList(access_token, new Callback<FriendListResponse>() {
            @Override
            public void success(FriendListResponse friends, Response response) {
                Log.v("CHECK", "SUCCESS");
                Log.v("RESTOFIT", friends.getResponse().toString());
                EventBus.getDefault().post(friends);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v("CHECK", "FAILURE" + error.getMessage());
            }
        });
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }
}
