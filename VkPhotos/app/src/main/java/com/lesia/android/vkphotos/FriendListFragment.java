package com.lesia.android.vkphotos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class FriendListFragment extends Fragment {
    ArrayAdapter<String> arrayAdapter;

    public FriendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);

        ArrayList<String> fakeData = new ArrayList<>();
        fakeData.add("Іван Іванов");
        fakeData.add("Петро Петров");
        fakeData.add("Назар Назарій");

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.friendListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        FriendListAdapter adapter = new FriendListAdapter(fakeData);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
