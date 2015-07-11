package com.lesia.android.vkphotos;

/**
 * Created by lesia on 7/11/15.
 */
public class OpenAlbumsFragmentEvent
{
    private String owner_id;

    public OpenAlbumsFragmentEvent(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(String owner_id) {
        this.owner_id = owner_id;
    }
}
