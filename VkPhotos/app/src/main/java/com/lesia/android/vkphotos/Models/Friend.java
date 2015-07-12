package com.lesia.android.vkphotos.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lesia on 7/9/15.
 */
public class Friend
{
    @SerializedName("first_name")
    private String mName;
    @SerializedName("last_name")
    private String mSurname;
    @SerializedName("photo_50")
    private String mPhotoUrl;
    @SerializedName("uid")
    private String mID;

    public Friend() {
        mName = "";
        mSurname = "";
        mPhotoUrl = "";
        mID = "";
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getFullName() {
        return mName + " " + mSurname;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "mName='" + mName + '\'' +
                ", mSurname='" + mSurname + '\'' +
                ", mPhotoUrl='" + mPhotoUrl + '\'' +
                ", mID='" + mID + '\'' +
                '}';
    }
}
