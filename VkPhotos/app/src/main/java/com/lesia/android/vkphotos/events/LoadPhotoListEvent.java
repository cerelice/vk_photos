package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/11/15.
 */
public class LoadPhotoListEvent {
    private String owner_id;
    private String album_id;
    private String access_token;

    public LoadPhotoListEvent(String owner_id, String album_id, String access_token) {
        this.owner_id = owner_id;
        this.album_id = album_id;
        this.access_token = access_token;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getAlbumId() {
        return album_id;
    }

    public void setAlbumId(String album_id) {
        this.album_id = album_id;
    }
}
