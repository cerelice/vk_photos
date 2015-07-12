package com.lesia.android.vkphotos.RestUtils;

import android.app.Application;
import android.util.Log;

import com.lesia.android.vkphotos.Models.AlbumsResponse;
import com.lesia.android.vkphotos.Events.LoadAlbumListEvent;
import com.lesia.android.vkphotos.Events.LoadFriendListEvent;
import com.lesia.android.vkphotos.Events.LoadPhotoListEvent;
import com.lesia.android.vkphotos.Models.FriendListResponse;
import com.lesia.android.vkphotos.Models.PhotoListResponse;

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

    public void onEvent(LoadAlbumListEvent event)
    {
        new RestClient().getApiService().getAlbums(event.getOwnerId(), new Callback<AlbumsResponse>() {
            @Override
            public void success(AlbumsResponse albums, Response response) {
                Log.v(LOG_TAG, "Success: " + albums.getResponse().toString());
                EventBus.getDefault().post(albums);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(LOG_TAG, "Failure: " + error.getMessage());
            }
        });
    }

    public void onEvent(LoadPhotoListEvent event)
    {
        new RestClient().getApiService().getPhotos(event.getOwnerId(), event.getAlbumId(), new Callback<PhotoListResponse>() {
            @Override
            public void success(PhotoListResponse photos, Response response) {
                Log.v(LOG_TAG, "Success: " + photos.getResponse().toString());
                EventBus.getDefault().post(photos);
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
