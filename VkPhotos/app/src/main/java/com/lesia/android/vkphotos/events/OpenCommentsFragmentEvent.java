package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/23/15.
 */
public class OpenCommentsFragmentEvent {
    private String ownerID;
    private String photoID;

    public OpenCommentsFragmentEvent(String ownerID, String photoID) {
        this.ownerID = ownerID;
        this.photoID = photoID;
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
}
