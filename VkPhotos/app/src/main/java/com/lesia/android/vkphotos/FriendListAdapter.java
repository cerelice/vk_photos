package com.lesia.android.vkphotos;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListItemViewHolder>
{
    private ArrayList<String> mDataset;
    private Drawable mDw1;
    private Drawable mDw2;

    public FriendListAdapter(ArrayList<String> Dataset, Drawable dw1, Drawable dw2) {
        mDataset = Dataset;
        mDw1 = dw1;
        mDw2 = dw2;
    }

    public FriendListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_friend, viewGroup, false);

        FriendListItemViewHolder vh = new FriendListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendListItemViewHolder viewHolder, int i) {
        viewHolder.mName.setText(mDataset.get(i));
        if(i % 2 == 0) {
            viewHolder.mPhoto.setImageDrawable(mDw1);
        }
        else {
            viewHolder.mPhoto.setImageDrawable(mDw2);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
