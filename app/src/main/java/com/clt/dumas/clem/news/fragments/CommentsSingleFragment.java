package com.clt.dumas.clem.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.viewmodels.CommentsViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class CommentsSingleFragment extends Fragment {
    private CommentsViewModel model;
    private TextView author;
    private TextView content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CommentsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout
        View view = inflater.inflate(R.layout.comment_single_fragment, container, false);
        //get fields
        author = view.findViewById(R.id.author);
        content = view.findViewById(R.id.content);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getSelected().observe(this, comments -> {
            //set fields
            author.setText(comments.getAuthor());
            content.setText(comments.getContent());
        });
    }
}
