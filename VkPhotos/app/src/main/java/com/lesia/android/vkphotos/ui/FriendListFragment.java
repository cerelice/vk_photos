package com.lesia.android.vkphotos.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);
        ((MainActivity)getActivity()).setUpNavigation(false);
        String access_token = getActivity().getSharedPreferences(
                    getString(R.string.shared_pref_file_name),
                    Context.MODE_PRIVATE
                )
                .getString(
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
                String access_token = getActivity().getSharedPreferences(
                        getString(R.string.shared_pref_file_name),
                        Context.MODE_PRIVATE
                ).getString(
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friend, menu);

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
    }

    public void onEvent(FriendListResponse friends)
    {
        swipeLayout.setRefreshing(false);
        Intent intent = getActivity().getIntent();
        if(intent != null && Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.v("SEARCH", "Query: " + query);
            ArrayList<Friend> resultList = new ArrayList<>();
            for(Friend friend: friends.getResponse()) {
                String name = friend.getName().toLowerCase();
                String surname = friend.getSurname().toLowerCase();
                query = query.toLowerCase();
                if(name.startsWith(query) || surname.startsWith(query)) {
                    resultList.add(friend);
                }
            }
            adapter.replaceAll(resultList);
        } else {
            adapter.replaceAll(friends.getResponse());
        }
    }
}
