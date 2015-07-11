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
    private static final String LOG_TAG = "APPLICATION";

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    public void onEvent(LoadFriendListEvent event)
    {
        new RestClient().getApiService().getFriendsList(event.getAccessToken(), new Callback<FriendListResponse>() {
            @Override
            public void success(FriendListResponse friends, Response response) {
                Log.v(LOG_TAG, "Success: " + friends.getResponse().toString());
                EventBus.getDefault().post(friends);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(LOG_TAG, "Failure: " + error.getMessage());
            }
        });
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }
}
