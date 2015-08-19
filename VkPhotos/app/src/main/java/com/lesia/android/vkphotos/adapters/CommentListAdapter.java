package com.lesia.android.vkphotos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.models.Comment;
import com.lesia.android.vkphotos.models.Friend;
import com.lesia.android.vkphotos.view_holders.CommentListItemViewHolder;

import java.util.ArrayList;

/**
 * Created by lesia on 7/23/15.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListItemViewHolder>
{
    private ArrayList<Comment> dataSet;
    private Context context;

    public CommentListAdapter(ArrayList<Comment> dataSet, Context context) {

        this.dataSet = dataSet;
        this.context = context;
        Log.v("onCreateViewHolder", dataSet.toString());
    }

    @Override
    public CommentListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_comment, parent, false);

        Log.v("onCreateViewHolder", dataSet.toString());

        CommentListItemViewHolder vh = new CommentListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CommentListItemViewHolder viewHolder, final int position) {
        Log.v("BIND_HOLDER", dataSet.get(position).toString());
        viewHolder.mName.setText(dataSet.get(position).getFullName());
        viewHolder.mText.setText(dataSet.get(position).getText());
        Glide.with(context).load(dataSet.get(position).getPhotoUrl()).into(viewHolder.mPhoto);
    }

    @Override
    public int getItemCount() {
        Log.v("getItemCount", "size: " + dataSet.size());
        return dataSet.size();
    }

    public void addAll(ArrayList<Comment> newComments)
    {
        dataSet.addAll(dataSet.size(), newComments);
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<Comment> newComments)
    {
        Log.v("replaceAll", newComments.toString());
        dataSet.clear();
        dataSet.addAll(dataSet.size(), newComments);
        Log.v("replaceAll", dataSet.toString());
        notifyDataSetChanged();
    }

    public void onEvent(Friend user) {

    }
}
