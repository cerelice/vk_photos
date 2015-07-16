package com.lesia.android.vkphotos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lesia.android.vkphotos.models.Friend;
import com.lesia.android.vkphotos.view_holders.FriendListItemViewHolder;
import com.lesia.android.vkphotos.events.OpenAlbumsFragmentEvent;
import com.lesia.android.vkphotos.R;

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

        FriendListItemViewHolder vh = new FriendListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final FriendListItemViewHolder viewHolder, final int position) {
        viewHolder.mName.setText(dataSet.get(position).getFullName());
        Glide.with(context).load(dataSet.get(position).getPhotoUrl()).into(viewHolder.mPhoto);
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenAlbumsFragmentEvent(dataSet.get(position).getID()));
            }
        });
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