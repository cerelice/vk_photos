package com.lesia.android.vkphotos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lesia.android.vkphotos.R;

/**
 * Created by lesia on 7/6/15.
 */
public class FriendListItemViewHolder extends RecyclerView.ViewHolder
{
    public ImageView mPhoto;
    public TextView mName;
    public View rootView;

    public FriendListItemViewHolder(View itemView) {
        super(itemView);

        mPhoto = (ImageView) itemView.findViewById(R.id.photoImageView);
        mName = (TextView) itemView.findViewById(R.id.nameTextView);
        rootView = itemView;
    }
}
