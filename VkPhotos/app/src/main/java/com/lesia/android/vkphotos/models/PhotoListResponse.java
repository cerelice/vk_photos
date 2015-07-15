package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lesia on 7/12/15.
 */
public class PhotoListResponse implements Serializable
{
    @SerializedName("response")
    private ArrayList<Photo> response;

    public PhotoListResponse() {
    }

    public PhotoListResponse(ArrayList<Photo> response) {
        this.response = response;
    }

    public ArrayList<Photo> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Photo> response) {
        this.response = response;
    }
}
