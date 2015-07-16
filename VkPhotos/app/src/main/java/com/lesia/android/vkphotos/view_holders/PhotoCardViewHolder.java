package com.lesia.android.vkphotos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lesia.android.vkphotos.R;

/**
 * Created by lesia on 7/12/15.
 */
public class PhotoCardViewHolder extends RecyclerView.ViewHolder
{
    public ImageView mPhoto;
    public View rootView;

    public PhotoCardViewHolder(View itemView) {
        super(itemView);

        mPhoto = (ImageView) itemView.findViewById(R.id.photoImageView);
        rootView = itemView;
    }
}
