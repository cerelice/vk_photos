package com.lesia.android.vkphotos.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lesia.android.vkphotos.R;

/**
 * Created by lesia on 7/23/15.
 */
public class CommentListItemViewHolder extends RecyclerView.ViewHolder
{
    public ImageView mPhoto;
    public TextView mName;
    public TextView mText;
    public View rootView;

    public CommentListItemViewHolder(View itemView) {
        super(itemView);

        mPhoto = (ImageView) itemView.findViewById(R.id.userPhotoImageButton);
        mName = (TextView) itemView.findViewById(R.id.userNameTextView);
        mText = (TextView) itemView.findViewById(R.id.commentBodyTextView);
        rootView = itemView;
    }
}
