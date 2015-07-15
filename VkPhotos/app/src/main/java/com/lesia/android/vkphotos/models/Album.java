package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lesia on 7/11/15.
 */
public class Album
{
    @SerializedName("thumb_src")
    private String mPhotoUrl;
    @SerializedName("title")
    private String mName;
    @SerializedName("aid")
    private String mID;
    @SerializedName("owner_id")
    private String mOwnerID;
    @SerializedName("sizes")
    private ArrayList<SpecialPhoto> photos;

    public ArrayList<SpecialPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<SpecialPhoto> photos) {
        this.photos = photos;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
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

    @Override
    public String toString() {
        return "Album{" +
                "mPhotoUrl='" + mPhotoUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mID='" + mID + '\'' +
                ", mOwnerID='" + mOwnerID + '\'' +
                ", photos=" + photos +
                '}';
    }
}
