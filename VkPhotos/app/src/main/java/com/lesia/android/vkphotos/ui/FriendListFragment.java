package com.lesia.android.vkphotos.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.adapters.FriendListAdapter;
import com.lesia.android.vkphotos.events.LoadFriendListEvent;
import com.lesia.android.vkphotos.models.Friend;
import com.lesia.android.vkphotos.models.FriendListResponse;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class FriendListFragment extends Fragment {
    private FriendListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

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
        ((MainActivity)getActivity()).setUpNavigation(false);
        String access_token = getActivity().getPreferences(Context.MODE_PRIVATE).getString(
                getString(R.string.access_token_key),
                getString(R.string.access_token_def_value)
        );
        EventBus.getDefault().post(new LoadFriendListEvent(access_token));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.friendListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FriendListAdapter(new ArrayList<Friend>(), getActivity());
        recyclerView.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.friendSwipeContainer);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String access_token = getActivity().getPreferences(Context.MODE_PRIVATE).getString(
                        getString(R.string.access_token_key),
                        getString(R.string.access_token_def_value)
                );
                EventBus.getDefault().post(new LoadFriendListEvent(access_token));
            }
        });

        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return rootView;
    }

    public void onEvent(FriendListResponse friends)
    {
        swipeLayout.setRefreshing(false);
        adapter.replaceAll(friends.getResponse());
    }
}
