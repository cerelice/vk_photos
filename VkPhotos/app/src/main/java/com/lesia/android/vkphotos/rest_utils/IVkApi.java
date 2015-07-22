package com.lesia.android.vkphotos.rest_utils;

import com.lesia.android.vkphotos.models.AlbumsResponse;
import com.lesia.android.vkphotos.models.FriendListResponse;
import com.lesia.android.vkphotos.models.LikeResponse;
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

    @GET("/photos.get?rev=1&extended=1")
    public void getPhotos(
            @Query("owner_id") String owner_id,
            @Query("album_id") String album_id,
            @Query("access_token") String access_token,
            Callback<PhotoListResponse> photoList
    );

    @GET("/likes.add?type=photo")
    public void likeAdd(
            @Query("owner_id") String owner_id,
            @Query("item_id") String item_id,
            @Query("access_token") String access_token,
            Callback<LikeResponse> photoList
    );

    @GET("/likes.delete?type=photo")
    public void likeDelete(
            @Query("owner_id") String owner_id,
            @Query("item_id") String item_id,
            @Query("access_token") String access_token,
            Callback<LikeResponse> photoList
    );

    @GET("/photos.getAlbums?need_covers=1&need_system=1&photo_sizes=1")
    public void getAlbums(
            @Query("owner_id") String owner_id,
            Callback<AlbumsResponse> albumList
    );
}
