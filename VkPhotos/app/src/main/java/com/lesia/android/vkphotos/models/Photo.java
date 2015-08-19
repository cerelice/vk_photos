package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lesia on 7/11/15.
 */
public class Photo implements Serializable
{
    @SerializedName("src_big")
    private String photoUrl;
    @SerializedName("pid")
    private String id;
    @SerializedName("owner_id")
    private String ownerID;
    @SerializedName("aid")
    private String albumID;
    @SerializedName("likes")
    private Likes likes;
    @SerializedName("comments")
    private Comments comments;

    public static class Likes implements Serializable
    {
        @SerializedName("user_likes")
        private int userLikes;
        @SerializedName("count")
        private int count;

        public int getUserLikes() {
            return userLikes;
        }

        public void setUserLikes(int userLikes) {
            this.userLikes = userLikes;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Likes{" +
                    "userLikes=" + userLikes +
                    ", count=" + count +
                    '}';
        }
    }

    public static class Comments implements Serializable {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Comments{" +
                    "count=" + count +
                    '}';
        }
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String mPhotoUrl) {
        this.photoUrl = mPhotoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String mOwnerID) {
        this.ownerID = mOwnerID;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String mAlbumID) {
        this.albumID = mAlbumID;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoUrl='" + photoUrl + '\'' +
                ", id='" + id + '\'' +
                ", ownerID='" + ownerID + '\'' +
                ", albumID='" + albumID + '\'' +
                ", likes=" + likes +
                '}';
    }
}
