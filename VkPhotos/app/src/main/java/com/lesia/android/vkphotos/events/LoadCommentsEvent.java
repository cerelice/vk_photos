package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/23/15.
 */
public class LoadCommentsEvent {
    private String ownerID;
    private String photoID;
    private String accessToken;

    public LoadCommentsEvent(String ownerID, String photoID, String accessToken) {
        this.ownerID = ownerID;
        this.photoID = photoID;
        this.accessToken = accessToken;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
