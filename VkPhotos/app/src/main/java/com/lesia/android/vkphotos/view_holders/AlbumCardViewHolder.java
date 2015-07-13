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
    public IListener mListener;

    public AlbumCardViewHolder(View itemView, IListener listener) {
        super(itemView);

        mCoverPhoto = (ImageView) itemView.findViewById(R.id.albumCoverImageView);
        mName = (TextView) itemView.findViewById(R.id.albumNameTextView);
        mListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(getPosition());
            }
        });
    }

    public interface IListener {
        void onClick(int pos);
    }
}
