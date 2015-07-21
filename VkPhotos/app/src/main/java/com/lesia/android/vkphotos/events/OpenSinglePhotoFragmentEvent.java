package com.lesia.android.vkphotos.events;

import com.lesia.android.vkphotos.models.PhotoListResponse;

import java.io.Serializable;

//TODO: declare OpenSinglePhotoFragmentEvent
public class OpenSinglePhotoFragmentEvent implements Serializable
{
    private PhotoListResponse photos;
    private int position;
    private int orientation;
    private int leftLocation;
    private int topLocation;
    private int width;
    private int height;

    public static final String PHOTOS_TAG = "PHOTOS";
    public static final String POSITION_TAG = "POSITION";
    public static final String ORIENTATION_TAG = "ORIENTATION";
    public static final String LEFT_LOCATION_TAG = "LEFT_LOCATION";
    public static final String TOP_LOCATION_TAG = "TOP_LOCATION";
    public static final String WIDTH_TAG = "WIDTH";
    public static final String HEIGHT_TAG = "HEIGHT";

    public OpenSinglePhotoFragmentEvent(PhotoListResponse photos,
                                        int position,
                                        int orientation,
                                        int leftLocation,
                                        int topLocation,
                                        int width,
                                        int height) {
        this.photos = photos;
        this.position = position;
        this.orientation = orientation;
        this.leftLocation = leftLocation;
        this.topLocation = topLocation;
        this.width = width;
        this.height = height;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getLeftLocation() {
        return leftLocation;
    }

    public void setLeftLocation(int leftLocation) {
        this.leftLocation = leftLocation;
    }

    public int getTopLocation() {
        return topLocation;
    }

    public void setTopLocation(int topLocation) {
        this.topLocation = topLocation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
