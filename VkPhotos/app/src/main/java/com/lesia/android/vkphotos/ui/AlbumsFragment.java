package com.lesia.android.vkphotos.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.adapters.AlbumCardAdapter;
import com.lesia.android.vkphotos.events.LoadAlbumListEvent;
import com.lesia.android.vkphotos.models.Album;
import com.lesia.android.vkphotos.models.AlbumsResponse;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class AlbumsFragment extends Fragment {
    SwipeRefreshLayout swipeLayout;
    AlbumCardAdapter adapter;
    RecyclerView recyclerView;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_albums, container, false);
        ((MainActivity)getActivity()).setUpNavigation(true);
        String owner_id = getArguments().getString("OWNER_ID");
        EventBus.getDefault().post(new LoadAlbumListEvent(owner_id));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.albumsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlbumCardAdapter(new ArrayList<Album>(), getActivity(), AlbumCardAdapter.CARD_LAYOUT_MODE);
        recyclerView.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.albumSwipeContainer);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String owner_id = getArguments().getString("OWNER_ID");
                EventBus.getDefault().post(new LoadAlbumListEvent(owner_id));
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
        inflater.inflate(R.menu.menu_albums, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                if(item.getTitle().equals("Change to list")) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new AlbumCardAdapter(
                                    adapter.getDataSet(),
                                    getActivity(),
                                    AlbumCardAdapter.LIST_LAYOUT_MODE
                    );
                    recyclerView.setAdapter(adapter);
                    item.setTitle("Change to grid");
                    return true;
                } else {
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new AlbumCardAdapter(
                            adapter.getDataSet(),
                            getActivity(),
                            AlbumCardAdapter.CARD_LAYOUT_MODE
                    );
                    recyclerView.setAdapter(adapter);
                    item.setTitle("Change to list");
                    return true;
                }
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public void onEvent(AlbumsResponse albums) {
        swipeLayout.setRefreshing(false);
        adapter.replaceAll(albums.getResponse());
    }
}
