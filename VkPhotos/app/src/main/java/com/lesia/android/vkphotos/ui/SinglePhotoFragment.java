package com.lesia.android.vkphotos.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesia.android.vkphotos.R;

public class SinglePhotoFragment extends Fragment {
    public SinglePhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_single_photo, container, false);
        ((MainActivity)getActivity()).setUpNavigation(true);
        return rootView;
    }
}
