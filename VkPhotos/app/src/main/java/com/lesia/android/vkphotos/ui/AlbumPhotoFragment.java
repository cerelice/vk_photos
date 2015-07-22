package com.lesia.android.vkphotos.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.adapters.PhotoCardAdapter;
import com.lesia.android.vkphotos.events.LoadPhotoListEvent;
import com.lesia.android.vkphotos.models.Photo;
import com.lesia.android.vkphotos.models.PhotoListResponse;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class AlbumPhotoFragment extends Fragment {
    private PhotoCardAdapter adapter;
    private RecyclerView recyclerView;

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
        ((MainActivity)getActivity()).setUpNavigation(true);

        String access_token = getActivity().getSharedPreferences(
                        getString(R.string.shared_pref_file_name),
                        Context.MODE_PRIVATE
                )
                .getString(
                        getString(R.string.access_token_key),
                        getString(R.string.access_token_def_value)
                );
        EventBus.getDefault().post(new LoadPhotoListEvent(
                getArguments().getString("OWNER_ID"),
                getArguments().getString("ALBUM_ID"),
                access_token
        ));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.photosRecyclerView);
        RecyclerView.LayoutManager layoutManager;
        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.photosLinearLayout);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 4);
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoCardAdapter(new ArrayList<Photo>(), getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
    }

    public void onEvent(PhotoListResponse photos)
    {
        adapter.addAll(photos.getResponse());
    }
}
