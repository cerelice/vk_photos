package com.lesia.android.vkphotos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lesia.android.vkphotos.R;

/**
 * Created by lesia on 7/11/15.
 */
public class AlbumCardViewHolder extends RecyclerView.ViewHolder
{
    public ImageView mCoverPhoto;
    public TextView mName;
    public View rootView;

    public AlbumCardViewHolder(View itemView) {
        super(itemView);

        mCoverPhoto = (ImageView) itemView.findViewById(R.id.albumCoverImageView);
        mName = (TextView) itemView.findViewById(R.id.albumNameTextView);
        rootView = itemView;
    }

}
