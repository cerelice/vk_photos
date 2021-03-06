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
import com.lesia.android.vkphotos.events.OpenPhotosFromAlbumEvent;
import com.lesia.android.vkphotos.models.Album;
import com.lesia.android.vkphotos.models.SpecialPhoto;
import com.lesia.android.vkphotos.view_holders.AlbumCardViewHolder;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by lesia on 7/11/15.
 */
public class AlbumCardAdapter extends RecyclerView.Adapter<AlbumCardViewHolder>
{
    private ArrayList<Album> dataSet;
    private Context context;
    private int layoutMode;
    public static final int LIST_LAYOUT_MODE = 0;
    public static final int CARD_LAYOUT_MODE = 1;

    public AlbumCardAdapter(ArrayList<Album> dataSet, Context context, int layoutMode) {
        this.dataSet = dataSet;
        this.context = context;
        this.layoutMode = layoutMode;
    }

    public int getLayoutMode() {
        return layoutMode;
    }

    public void setLayoutMode(int layoutMode) {
        this.layoutMode = layoutMode;
        notifyAll();
    }

    public AlbumCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        if(layoutMode == LIST_LAYOUT_MODE) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_album, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_album, viewGroup, false);
        }

        AlbumCardViewHolder vh = new AlbumCardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AlbumCardViewHolder viewHolder, final int position) {
        viewHolder.mName.setText(dataSet.get(position).getName());

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenPhotosFromAlbumEvent(
                        dataSet.get(position).getOwnerID(),
                        dataSet.get(position).getID()
                ));
            }
        });

        ArrayList<SpecialPhoto> photos = dataSet.get(position).getPhotos();
        String photo_url = null;
        for(SpecialPhoto photo : photos) {
            if(photo.getType().equals("x")) {
                photo_url = photo.getSrc();
                break;
            }
        }
        if(layoutMode == LIST_LAYOUT_MODE) {
            Glide.with(context).load(photo_url).asBitmap().centerCrop().into(new BitmapImageViewTarget(viewHolder.mCoverPhoto) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(Math.max(resource.getWidth(), resource.getHeight()) / 2.0f);
                    viewHolder.mCoverPhoto.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            Glide.with(context).load(photo_url).centerCrop().into(viewHolder.mCoverPhoto);
        }
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

    public void replaceAll(ArrayList<Album> newAlbums)
    {
        dataSet.clear();
        dataSet.addAll(dataSet.size(), newAlbums);
        notifyDataSetChanged();
    }

    public ArrayList<Album> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<Album> dataSet) {
        this.dataSet = dataSet;
    }
}
