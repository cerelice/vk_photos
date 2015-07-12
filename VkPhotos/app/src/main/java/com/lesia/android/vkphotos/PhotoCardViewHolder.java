package com.lesia.android.vkphotos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lesia on 7/12/15.
 */
public class PhotoCardViewHolder extends RecyclerView.ViewHolder
{
    public ImageView mPhoto;

    public PhotoCardViewHolder(View itemView) {
        super(itemView);

        mPhoto = (ImageView) itemView.findViewById(R.id.photoImageView);
    }
}
