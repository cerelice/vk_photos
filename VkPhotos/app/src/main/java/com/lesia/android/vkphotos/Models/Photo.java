package com.lesia.android.vkphotos.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lesia on 7/11/15.
 */
public class Photo
{
    @SerializedName("src")
    private String mPhotoUrl;
    @SerializedName("pid")
    private String mID;
    @SerializedName("owner_id")
    private String mOwnerID;
    @SerializedName("aid")
    private String mAlbumID;

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getOwnerID() {
        return mOwnerID;
    }

    public void setOwnerID(String mOwnerID) {
        this.mOwnerID = mOwnerID;
    }

    public String getAlbumID() {
        return mAlbumID;
    }

    public void setAlbumID(String mAlbumID) {
        this.mAlbumID = mAlbumID;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mPhotoUrl='" + mPhotoUrl + '\'' +
                ", mID='" + mID + '\'' +
                ", mOwnerID='" + mOwnerID + '\'' +
                ", mAlbumID='" + mAlbumID + '\'' +
                '}';
    }
}
