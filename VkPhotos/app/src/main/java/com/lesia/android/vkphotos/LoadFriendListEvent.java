package com.lesia.android.vkphotos;

/**
 * Created by lesia on 7/11/15.
 */
public class LoadFriendListEvent
{
    private String access_token;

    public LoadFriendListEvent(String access_token) {
        this.access_token = access_token;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

}
