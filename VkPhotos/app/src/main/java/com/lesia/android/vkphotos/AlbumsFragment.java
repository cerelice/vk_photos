package com.lesia.android.vkphotos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class AlbumsFragment extends Fragment {
    AlbumCardAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_albums, container, false);
        EventBus.getDefault().post(new LoadAlbumListEvent(getArguments().getString("OWNER_ID")));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.albumsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlbumCardAdapter(new ArrayList<Album>(), getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void onEvent(AlbumsResponse albumsResponse)
    {
        adapter.addAll(albumsResponse.getResponse());
    }

}
