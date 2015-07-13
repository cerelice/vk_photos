package com.lesia.android.vkphotos.rest_utils;

import com.lesia.android.vkphotos.models.AlbumsResponse;
import com.lesia.android.vkphotos.models.FriendListResponse;
import com.lesia.android.vkphotos.models.PhotoListResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface IVkApi
{
    @GET("/friends.get?order=hints&fields=photo_50")
    public void getFriendsList(
            @Query("access_token") String access_token,
            Callback<FriendListResponse> friendList
    );

    @GET("/photos.get?rev=0")
    public void getPhotos(
            @Query("owner_id") String owner_id,
            @Query("album_id") String album_id,
            Callback<PhotoListResponse> photoList
    );

    @GET("/photos.getAlbums?need_covers=1&need_system=1")
    public void getAlbums(
            @Query("owner_id") String owner_id,
            Callback<AlbumsResponse> albumList
    );
}
