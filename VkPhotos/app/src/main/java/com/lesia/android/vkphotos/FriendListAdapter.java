package com.lesia.android.vkphotos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListItemViewHolder>
{
    private ArrayList<String> mDataset;

    public FriendListAdapter(ArrayList<String> Dataset) {
        mDataset = Dataset;
    }

    public FriendListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_friend, viewGroup, false);

        FriendListItemViewHolder vh = new FriendListItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendListItemViewHolder viewHolder, int i) {
        viewHolder.mName.setText(mDataset.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<String> getDataset() { return mDataset; }

    public void addItem(String n) {
        mDataset.add(n);
        notifyItemInserted(mDataset.size()-1);
    }

    public void removeAll() {
        int size = mDataset.size();
        for(int i = 0; i < size; i++) {
            mDataset.remove(0);
            notifyItemRemoved(0);
        }
    }
}
