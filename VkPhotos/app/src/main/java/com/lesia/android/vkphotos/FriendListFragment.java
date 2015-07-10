package com.lesia.android.vkphotos;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class FriendListFragment extends Fragment {
    FriendListAdapter adapter;

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

    public FriendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);

        String access_token = getActivity().getPreferences(Context.MODE_PRIVATE).getString(
                getString(R.string.access_token_key),
                getString(R.string.access_token_def_value)
        );
        Log.v("USER_ID", access_token);
        EventBus.getDefault().post(access_token);

        ArrayList<String> fakeData = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            fakeData.add("Friend " + i);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.friendListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Drawable dw1 = getActivity().getResources().getDrawable(R.drawable.abc_ic_go_search_api_mtrl_alpha);
        Drawable dw2 = getActivity().getResources().getDrawable(R.drawable.abc_ic_menu_cut_mtrl_alpha);
        adapter = new FriendListAdapter(new ArrayList<Friend>(), getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void onEvent(FriendListResponse response)
    {
        Log.v("adapter", response.getResponse().toString());
        adapter.addAll(response.getResponse());
    }

}
