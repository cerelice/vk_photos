package com.lesia.android.vkphotos.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lesia on 7/24/15.
 */
public class LoadUserInfoEvent {
    @SerializedName("user_ids")
    private String userID;

    public LoadUserInfoEvent(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
