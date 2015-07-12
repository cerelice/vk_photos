package com.lesia.android.vkphotos.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lesia.android.vkphotos.Models.Album;
import com.lesia.android.vkphotos.ViewHolders.AlbumCardViewHolder;
import com.lesia.android.vkphotos.Events.OpenPhotosFromAlbumEvent;
import com.lesia.android.vkphotos.R;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by lesia on 7/11/15.
 */
public class AlbumCardAdapter extends RecyclerView.Adapter<AlbumCardViewHolder>
{
    private ArrayList<Album> dataSet;
    private Context context;

    public AlbumCardAdapter(ArrayList<Album> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    public AlbumCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_album, viewGroup, false);

        AlbumCardViewHolder vh = new AlbumCardViewHolder(v, new AlbumCardViewHolder.IListener() {
            @Override
            public void onClick(int pos) {
                EventBus.getDefault().post(new OpenPhotosFromAlbumEvent(
                        dataSet.get(pos).getOwnerID(),
                        dataSet.get(pos).getID()
                ));
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(AlbumCardViewHolder viewHolder, int i) {
        viewHolder.mName.setText(dataSet.get(i).getName());
        Glide.with(context).load(dataSet.get(i).getPhotoUrl()).centerCrop().into(viewHolder.mCoverPhoto);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addAll(ArrayList<Album> newAlbums)
    {
        dataSet.addAll(dataSet.size(), newAlbums);
        notifyDataSetChanged();
    }
}
