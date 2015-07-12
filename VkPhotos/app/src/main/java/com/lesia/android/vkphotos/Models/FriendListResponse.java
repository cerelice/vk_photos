package com.lesia.android.vkphotos.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lesia on 7/10/15.
 */
public class FriendListResponse
{
    @SerializedName("response")
    private ArrayList<Friend> response;

    public ArrayList<Friend> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Friend> response) {
        this.response = response;
    }
}
