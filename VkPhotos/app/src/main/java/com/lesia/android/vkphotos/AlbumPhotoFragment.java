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

public class AlbumPhotoFragment extends Fragment {
    PhotoCardAdapter adapter;

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

    public AlbumPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album_photo, container, false);
        EventBus.getDefault().post(new LoadPhotoListEvent(
                getArguments().getString("OWNER_ID"),
                getArguments().getString("ALBUM_ID")
        ));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.photosRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoCardAdapter(new ArrayList<Photo>(), getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void onEvent(PhotoListResponse event)
    {
        adapter.addAll(event.getResponse());
    }
}
