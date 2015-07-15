package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lesia on 7/11/15.
 */
public class Photo implements Serializable
{
    @SerializedName("src_big")
    private String photoUrl;
    @SerializedName("pid")
    private String id;
    @SerializedName("owner_id")
    private String ownerID;
    @SerializedName("aid")
    private String albumID;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.photoUrl = mPhotoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String mOwnerID) {
        this.ownerID = mOwnerID;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String mAlbumID) {
        this.albumID = mAlbumID;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoUrl='" + photoUrl + '\'' +
                ", id='" + id + '\'' +
                ", ownerID='" + ownerID + '\'' +
                ", albumID='" + albumID + '\'' +
                '}';
    }
}
