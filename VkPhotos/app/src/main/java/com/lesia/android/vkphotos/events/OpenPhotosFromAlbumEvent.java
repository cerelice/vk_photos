package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/11/15.
 */
public class OpenPhotosFromAlbumEvent
{
    private String owner_id;
    private String album_id;

    public OpenPhotosFromAlbumEvent(String owner_id, String album_id) {
        this.owner_id = owner_id;
        this.album_id = album_id;
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
