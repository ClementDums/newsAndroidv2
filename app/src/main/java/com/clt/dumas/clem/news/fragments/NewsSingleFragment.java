package com.clt.dumas.clem.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.viewmodels.NewsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

//News single fragment
public class NewsSingleFragment extends Fragment implements NewsListener {
    private NewsViewModel model;
    private TextView titleTv;
    private TextView descriptionTv;
    private ImageView image;
    private ImageView share;
    private ImageView like;
    private ImageView comments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get News view model
        model= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NewsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout
        View view = inflater.inflate(R.layout.news_single_fragment, container, false);
        //get fields
        titleTv =view.findViewById(R.id.name);
        descriptionTv = view.findViewById(R.id.description);
        image= view.findViewById(R.id.imageSingle);
        share= view.findViewById(R.id.share);
        like = view.findViewById(R.id.like);
        comments = view.findViewById(R.id.comments);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getSelected().observe(this,news -> {
            //set fields
            titleTv.setText(news.getTitle());
            descriptionTv.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).fit().centerCrop().into(image);
            if (!news.isLike()) like.setImageResource(R.drawable.ic_action_name);
            else like.setImageResource(R.drawable.ic_like_true);

            share.setOnClickListener(v -> onShare(news));
            like.setOnClickListener(v -> onLike(news, news.isLike()));
            comments.setOnClickListener(v -> onComment(news));
        });
    }

    @Override
    public void onShare(News news) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        //Share url
        sendIntent.putExtra(Intent.EXTRA_TEXT, news.getUrl());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onSelect(News news) { }

    @Override
    public void onLike(News news, boolean isLiked){
        model.setFav(news,isLiked);
        if (!news.isLike()) like.setImageResource(R.drawable.ic_action_name);
        else like.setImageResource(R.drawable.ic_like_true);
    }

    @Override
    public void onComment(News news) {
        model.setSelected(news);
        Fragment fragment = new CommentsListFragment();
        replaceFragment(fragment);
    }

    /**
     *Replace a fragment
     * @param someFragment The fragment
     */
    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = null;
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        }
        if (transaction != null) {
            transaction.replace(R.id.fragment_container, someFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
