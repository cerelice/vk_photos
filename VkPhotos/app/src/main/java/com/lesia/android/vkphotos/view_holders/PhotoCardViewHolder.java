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
    public IListener mListener;

    public PhotoCardViewHolder(View itemView, IListener listener) {
        super(itemView);

        mPhoto = (ImageView) itemView.findViewById(R.id.photoImageView);
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
