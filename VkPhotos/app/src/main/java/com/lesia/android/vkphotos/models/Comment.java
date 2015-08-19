package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lesia on 7/23/15.
 */
public class Comment {
    @SerializedName("id")
    private String id;
    @SerializedName("from_id")
    private String fromID;
    @SerializedName("text")
    private String text;

    private String fullName;
    private String photoUrl;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", fromID='" + fromID + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
