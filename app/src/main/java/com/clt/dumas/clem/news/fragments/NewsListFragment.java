package com.clt.dumas.clem.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.adapters.NewsAdapter;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Fragment News list
 */
public class NewsListFragment extends Fragment implements NewsListener {
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;
    private NewsViewModel model;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create new viewmodel or load one
        model= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NewsViewModel.class);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        //reload datas
        model.loadNewsDb();
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
        model.getnews().observe(this, newsList -> {
            adapter.setNewsList(newsList);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     *Init Recyclerview, adapter and add fav option
     * @param view
     */
    private void init(View view) {
        //init recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NewsAdapter(newsList, this);
        //Associate adapter and item orientation
        ImageView favPage = view.findViewById(R.id.fav);
        //bring add fav to first layer
        favPage.bringToFront();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //Set add fav onClick
        favPage.setOnClickListener(v -> {
            Fragment favFragment = new FavsListFragment();
            replaceFragment(favFragment);
        });
    }

    /**
     *On share news
     * @param news The news to share
     */
    @Override
    public void onShare(News news) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        //Share url
        sendIntent.putExtra(Intent.EXTRA_TEXT, news.getUrl());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    /**
     *On Select replace with single fragment
     * @param news
     */
    @Override
    public void onSelect(News news) {
        model.setSelected(news);
        Fragment fragment = new NewsSingleFragment();
        replaceFragment(fragment);
    }

    /**
     *OnClick on like/dislike from Favs
     * @param news
     * @param isLiked
     */
    public void onLike(News news, boolean isLiked){
        model.setFav(news,isLiked);
    }


    /**
     *OnComment see comments from an article
     * @param news
     */
    @Override
    public void onComment(News news) {
        model.setSelected(news);
        Fragment fragment = new CommentsSingleFragment();
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
