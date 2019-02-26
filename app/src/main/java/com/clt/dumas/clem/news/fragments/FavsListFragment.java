package com.clt.dumas.clem.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.adapters.NewsAdapter;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.viewmodels.FavsViewModel;

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
 * Fragment Favs list
 */
public class FavsListFragment extends Fragment implements NewsListener {
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;
    private FavsViewModel model;

    /***
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create new viewmodel or load one
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavsViewModel.class);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favs_list_fragment, container, false);
        init(view);
        return view;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**
         * il est préférable d'observer la liste des articles avec getViewLifecycleOwner() au lieu de this
         * pourquoi?
         * en this faire référence au fragment qui ne se détruit pas tout de suite et par conséquent
         * tu continues d'obsrver les changements sur cette liste même quand la vue est détruite
         * par contre, si ton tu observes avec getViewLifecycleOwner(), quand la vue est détruite
         * comme par exemple, lors d'un changement de frgment, ton fragment arrête d'observer les
         * changements sur ce livedata.
         * Alors, à quel moment faut-il observer avec this ou avec getViewLifecycleOwner()??
         * La réponse est simple:
         * 1. si les changments nécessitent de modifier la vue, utilise getViewLifecycleOwner()
         * 2. sinon, utilise this
         */
        model.getFavs().observe(getViewLifecycleOwner(), newsList -> {
            adapter.setNewsList(newsList);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * Init Recyclerview and adapter
     * @param view
     */
    private void init(View view) {
        //init recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_fav);
        //init adapter
        adapter = new NewsAdapter(newsList, this);
        //Associate adapter and item orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * On share news
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
     * On Select replace with single fragment
     * @param news the clicked news
     */
    @Override
    public void onSelect(News news) {
        model.setSelected(news);
        Fragment fragment = new FavsSingleFragment();
        replaceFragment(fragment);
    }

    /**
     * On dislike from Favs
     * @param news
     * @param isliked
     */
    @Override
    public void onLike(News news, boolean isliked) {
        //@eamosse le nom de la méthode ne correspond pas à ce qu'il doit faire
        model.removeFav(news);
    }

    @Override
    public void onComment(News news) {

    }

    /**
     * Replace a fragment
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
