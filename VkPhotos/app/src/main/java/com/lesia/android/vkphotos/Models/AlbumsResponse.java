package com.lesia.android.vkphotos.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lesia on 7/11/15.
 */
public class AlbumsResponse {
    @SerializedName("response")
    private ArrayList<Album> response;

    public ArrayList<Album> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Album> response) {
        this.response = response;
    }
}
