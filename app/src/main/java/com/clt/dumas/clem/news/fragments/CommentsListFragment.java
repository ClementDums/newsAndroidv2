package com.clt.dumas.clem.news.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.adapters.CommentsAdapter;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.Comments;
import com.clt.dumas.clem.news.viewmodels.CommentsViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsListFragment extends Fragment {
    private List<Comments> comments;
    private CommentsAdapter adapter;
    private CommentsViewModel model;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creates a new viewmodel or load one
        model= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CommentsViewModel.class);
        model.getComments().observe(this, commentsList -> {
            adapter.setCommentsList(commentsList);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_list_fragment, container, false);
        //reload datas
        model.loadCommentsDB();
        init(view);

        return view;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Inits Recyclerview and adapter
     * @param view
     */
    private void init(View view) {
        //init recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new CommentsAdapter(comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
