package com.lesia.android.vkphotos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lesia.android.vkphotos.events.OpenSinglePhotoFragmentEvent;
import com.lesia.android.vkphotos.models.Photo;
import com.lesia.android.vkphotos.models.PhotoListResponse;
import com.lesia.android.vkphotos.view_holders.PhotoCardViewHolder;
import com.lesia.android.vkphotos.R;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class PhotoCardAdapter extends RecyclerView.Adapter<PhotoCardViewHolder>
{
    private ArrayList<Photo> dataSet;
    private Context context;

    public PhotoCardAdapter(ArrayList<Photo> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    public PhotoCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_photo, viewGroup, false);

        PhotoCardViewHolder vh = new PhotoCardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PhotoCardViewHolder viewHolder, final int position) {
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(
                        new OpenSinglePhotoFragmentEvent(
                                new PhotoListResponse(dataSet),
                                position
                        )
                );
            }
        });
        Glide.with(context).load(dataSet.get(position).getPhotoUrl()).centerCrop().into(viewHolder.mPhoto);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addAll(ArrayList<Photo> newPhotos)
    {
        dataSet.addAll(dataSet.size(), newPhotos);
        notifyDataSetChanged();
    }
}