package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lesia on 7/12/15.
 */
public class PhotoListResponse {
    @SerializedName("response")
    private ArrayList<Photo> response;

    public ArrayList<Photo> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Photo> response) {
        this.response = response;
    }
}
