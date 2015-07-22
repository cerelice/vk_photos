package com.lesia.android.vkphotos.rest_utils;

import android.app.Application;
import android.util.Log;

import com.lesia.android.vkphotos.events.AddLikeEvent;
import com.lesia.android.vkphotos.events.DeleteLikeEvent;
import com.lesia.android.vkphotos.events.LikeCountChangedEvent;
import com.lesia.android.vkphotos.models.AlbumsResponse;
import com.lesia.android.vkphotos.events.LoadAlbumListEvent;
import com.lesia.android.vkphotos.events.LoadFriendListEvent;
import com.lesia.android.vkphotos.events.LoadPhotoListEvent;
import com.lesia.android.vkphotos.models.FriendListResponse;
import com.lesia.android.vkphotos.models.LikeResponse;
import com.lesia.android.vkphotos.models.PhotoListResponse;

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
    private static final IVkApi apiService = new RestClient().getApiService();

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    public void onEvent(LoadFriendListEvent event)
    {
        apiService.getFriendsList(event.getAccessToken(), new Callback<FriendListResponse>() {
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
        apiService.getAlbums(event.getOwnerId(), new Callback<AlbumsResponse>() {
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
        apiService.getPhotos(event.getOwnerId(), event.getAlbumId(), event.getAccessToken(), new Callback<PhotoListResponse>() {
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

    public void onEvent(AddLikeEvent event)
    {
        apiService.likeAdd(event.getOwnerId(), event.getItemId(), event.getAccessToken(), new Callback<LikeResponse>() {
            @Override
            public void success(LikeResponse likeResponse, Response response) {
                Log.v(LOG_TAG, "Success: " + likeResponse.toString());
                EventBus.getDefault().post(new LikeCountChangedEvent(
                        likeResponse.getResponse().getLikes(),
                        LikeCountChangedEvent.ADD_LIKE
                ));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(LOG_TAG, "Failure: " + error.getMessage());
            }
        });
    }

    public void onEvent(DeleteLikeEvent event)
    {
        apiService.likeDelete(event.getOwnerId(), event.getItemId(), event.getAccessToken(), new Callback<LikeResponse>() {
            @Override
            public void success(LikeResponse likeResponse, Response response) {
                Log.v(LOG_TAG, "Success: " + likeResponse.toString());
                EventBus.getDefault().post(new LikeCountChangedEvent(
                        likeResponse.getResponse().getLikes(),
                        LikeCountChangedEvent.DELETE_LIKE
                ));
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
