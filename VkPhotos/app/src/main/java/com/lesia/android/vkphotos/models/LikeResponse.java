package com.lesia.android.vkphotos.models;

/**
 * Created by lesia on 7/22/15.
 */
public class LikeResponse {
    private LikeCount response;

    public LikeCount getResponse() {
        return response;
    }

    public void setResponse(LikeCount response) {
        this.response = response;
    }

    public class LikeCount {
        private int likes;

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        @Override
        public String toString() {
            return "LikeCount{" +
                    "likes=" + likes +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LikeResponse{" +
                "response=" + response +
                '}';
    }
}
