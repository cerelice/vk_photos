package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/22/15.
 */

public class DeleteLikeEvent {
    private String ownerId;
    private String itemId;
    private String accessToken;

    public DeleteLikeEvent(String ownerId, String itemId, String accessToken) {
        this.ownerId = ownerId;
        this.itemId = itemId;
        this.accessToken = accessToken;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
