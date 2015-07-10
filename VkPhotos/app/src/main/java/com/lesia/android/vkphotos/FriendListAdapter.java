package com.lesia.android.vkphotos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListItemViewHolder>
{
    private ArrayList<Friend> mDataset;
    private Context context;
    /*
    private Drawable mDw1;
    private Drawable mDw2;
    */

    public FriendListAdapter(ArrayList<Friend> Dataset, Context context) {
        mDataset = Dataset;
        this.context = context;
        //mDw1 = dw1;
        //mDw2 = dw2;
    }

    public FriendListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_friend, viewGroup, false);

        FriendListItemViewHolder vh = new FriendListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendListItemViewHolder viewHolder, int i) {
        viewHolder.mName.setText(mDataset.get(i).getFullName());
        Glide.with(context).load(mDataset.get(i).getPhotoUrl()).into(viewHolder.mPhoto);
        /*
        if(i % 2 == 0) {
            viewHolder.mPhoto.setImageDrawable(mDw1);
        }
        else {
            viewHolder.mPhoto.setImageDrawable(mDw2);
        }
        */
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addAll(ArrayList<Friend> newFriends)
    {
        mDataset.addAll(0, newFriends);
        notifyDataSetChanged();
    }
}
