package com.lesia.android.vkphotos.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesia.android.vkphotos.adapters.FriendListAdapter;
import com.lesia.android.vkphotos.events.LoadFriendListEvent;
import com.lesia.android.vkphotos.models.Friend;
import com.lesia.android.vkphotos.models.FriendListResponse;
import com.lesia.android.vkphotos.R;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class FriendListFragment extends Fragment {
    private FriendListAdapter adapter;

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

        return rootView;
    }

    public void onEvent(FriendListResponse friends)
    {
        adapter.addAll(friends.getResponse());
    }
}
