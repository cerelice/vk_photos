package com.lesia.android.vkphotos.events;

import com.lesia.android.vkphotos.models.PhotoListResponse;

//TODO: declare OpenSinglePhotoFragmentEvent
public class OpenSinglePhotoFragmentEvent {
    private PhotoListResponse photos;
    private int position;

    public OpenSinglePhotoFragmentEvent(PhotoListResponse photos, int position) {
        this.photos = photos;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PhotoListResponse getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoListResponse photos) {
        this.photos = photos;
    }
}
