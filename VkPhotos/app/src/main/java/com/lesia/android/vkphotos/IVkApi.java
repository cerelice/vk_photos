package com.lesia.android.vkphotos;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface IVkApi
{
    @GET("/friends.get?order=hints&fields=photo_50")
    public void getFriendsList(@Query("access_token") String access_token, Callback<FriendListResponse> friendList);
}
