package com.lesia.android.vkphotos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.events.OpenSinglePhotoFragmentEvent;
import com.lesia.android.vkphotos.models.Photo;
import com.lesia.android.vkphotos.models.PhotoListResponse;
import com.lesia.android.vkphotos.view_holders.PhotoCardViewHolder;

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
                int[] screenLocation = new int[2];
                viewHolder.mPhoto.getLocationOnScreen(screenLocation);
                int orientation = context.getResources().getConfiguration().orientation;
                if(viewHolder.mPhoto.getDrawable() == null) {
                    Log.v("DRAWABLE", "NULL DRAWABLE ON PHOTO_CARD_ADAPTER");
                } else {
                    Log.v("DRAWABLE", "DRAWABLE ON PHOTO_CARD_ADAPTER IS OK");
                }
                EventBus.getDefault().postSticky(viewHolder.mPhoto.getDrawable());
                EventBus.getDefault().post(
                        new OpenSinglePhotoFragmentEvent(
                                new PhotoListResponse(dataSet),
                                position,
                                orientation,
                                screenLocation[0],
                                screenLocation[1],
                                viewHolder.mPhoto.getWidth(),
                                viewHolder.mPhoto.getHeight()
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
