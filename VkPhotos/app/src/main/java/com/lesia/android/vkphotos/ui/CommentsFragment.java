package com.lesia.android.vkphotos.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesia.android.vkphotos.R;
import com.lesia.android.vkphotos.adapters.CommentListAdapter;
import com.lesia.android.vkphotos.events.LoadCommentsEvent;
import com.lesia.android.vkphotos.models.Comment;
import com.lesia.android.vkphotos.models.CommentListResponse;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {
    private CommentListAdapter adapter;

    public CommentsFragment() {
        // Required empty public constructor
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);

        String access_token = getActivity().getSharedPreferences(
                getString(R.string.shared_pref_file_name),
                Context.MODE_PRIVATE
                )
                .getString(
                        getString(R.string.access_token_key),
                        getString(R.string.access_token_def_value)
                );
        EventBus.getDefault().post(new LoadCommentsEvent(
                //getActivity().getIntent().getStringExtra("OWNER_ID"),
                //getActivity().getIntent().getStringExtra("PHOTO_ID"),
                getArguments().getString("OWNER_ID"),
                getArguments().getString("PHOTO_ID"),
                access_token
        ));
        ArrayList<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setFromID("fromID");
        comment.setText("texttexttexttexttexttext" + "\n" +
                "texttexttexttexttexttexttexttext" + "\n" +
                "texttexttexttexttexttexttexttext" + "\n" +
                "texttexttexttexttexttexttexttext" + "\n" +
                "texttexttexttexttexttext");

        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.commentsListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentListAdapter(new ArrayList<Comment>(), getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void onEvent(CommentListResponse event) {
        Log.v("CommentListResponse", event.getResponse().getItems().toString());
        adapter.replaceAll(event.getResponse().getItems());
    }
}
