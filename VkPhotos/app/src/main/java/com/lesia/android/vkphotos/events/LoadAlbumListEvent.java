package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/11/15.
 */
public class LoadAlbumListEvent {
    private String owner_id;

    public LoadAlbumListEvent(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(String owner_id) {
        this.owner_id = owner_id;
    }
}
