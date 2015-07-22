package com.lesia.android.vkphotos.events;

/**
 * Created by lesia on 7/22/15.
 */
public class LikeCountChangedEvent {
    private int likesCount;
    private int action;

    public static final int DELETE_LIKE = 0;
    public static final int ADD_LIKE = 1;

    public LikeCountChangedEvent(int likesCount, int action) {
        this.likesCount = likesCount;
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    @Override
    public String toString() {
        return "LikeCountChangedEvent{" +
                "likesCount=" + likesCount +
                ", action=" + action +
                '}';
    }
}
