package com.lesia.android.vkphotos.ViewHolders;

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
    public IListener mListener;

    public FriendListItemViewHolder(View itemView, IListener listener) {
        super(itemView);

        mPhoto = (ImageView) itemView.findViewById(R.id.photoImageView);
        mName = (TextView) itemView.findViewById(R.id.nameTextView);
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
