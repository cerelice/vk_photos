package com.lesia.android.vkphotos;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FriendListFragment extends Fragment {

    public FriendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);

        ArrayList<String> fakeData = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            fakeData.add("Friend " + i);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.friendListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Drawable dw1 = getActivity().getResources().getDrawable(R.drawable.abc_ic_go_search_api_mtrl_alpha);
        Drawable dw2 = getActivity().getResources().getDrawable(R.drawable.abc_ic_menu_cut_mtrl_alpha);
        FriendListAdapter adapter = new FriendListAdapter(fakeData, dw1, dw2);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
