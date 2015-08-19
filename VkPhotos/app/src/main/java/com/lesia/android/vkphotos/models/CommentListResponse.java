package com.lesia.android.vkphotos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lesia on 7/23/15.
 */
public class CommentListResponse {
    @SerializedName("response")
    private Items response;

    public static class Items {
        @SerializedName("count")
        private int count;
        @SerializedName("items")
        private ArrayList<Comment> items;

        public ArrayList<Comment> getItems() {
            return items;
        }

        public void setItems(ArrayList<Comment> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "Items{" +
                    "count=" + count +
                    ", items=" + items +
                    '}';
        }
    }

    public Items getResponse() {
        return response;
    }

    public void setResponse(Items response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "CommentListResponse{" +
                "response=" + response +
                '}';
    }
}
