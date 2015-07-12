package com.lesia.android.vkphotos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by lesia on 7/12/15.
 */
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
    public void onBindViewHolder(PhotoCardViewHolder viewHolder, int i) {
        Glide.with(context).load(dataSet.get(i).getPhotoUrl()).centerCrop().into(viewHolder.mPhoto);
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
