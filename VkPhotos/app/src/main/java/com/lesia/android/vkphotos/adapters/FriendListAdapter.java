package com.lesia.android.vkphotos.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.events.OpenAlbumsFragmentEvent;
import com.lesia.android.vkphotos.models.Friend;
import com.lesia.android.vkphotos.view_holders.FriendListItemViewHolder;

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
        Glide.with(context).load(dataSet.get(position).getPhotoUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(viewHolder.mPhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(Math.max(resource.getWidth(), resource.getHeight()) / 2.0f);
                viewHolder.mPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });
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

    public void replaceAll(ArrayList<Friend> newFriends)
    {
        dataSet.clear();
        dataSet.addAll(dataSet.size(), newFriends);
        notifyDataSetChanged();
    }


    public void setDataSet(ArrayList<Friend> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }
}
