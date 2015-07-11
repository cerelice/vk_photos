package com.lesia.android.vkphotos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListItemViewHolder>
{
    private ArrayList<Friend> dataSet;
    private Context context;

    public FriendListAdapter(ArrayList<Friend> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    public FriendListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_friend, viewGroup, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenAlbumsFragmentEvent(dataSet.get(i).getID()));
            }
        });

        FriendListItemViewHolder vh = new FriendListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendListItemViewHolder viewHolder, int i) {
        viewHolder.mName.setText(dataSet.get(i).getFullName());
        Glide.with(context).load(dataSet.get(i).getPhotoUrl()).into(viewHolder.mPhoto);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addAll(ArrayList<Friend> newFriends)
    {
        dataSet.addAll(dataSet.size(), newFriends);
        notifyDataSetChanged();
    }
}
