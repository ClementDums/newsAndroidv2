package com.clt.dumas.clem.news.fragments;

import android.content.Intent;
import android.net.Uri;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        //creer nouveau viewmodel ou charger un existant
        model= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavsViewModel.class);

    }

    /**
     *
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
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getFavs().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> newsList) {
                adapter.setNewsList(newsList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     *
     * @param view
     */
    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_fav);
        adapter = new NewsAdapter(newsList, this);
        //Associer adapteur et orientation elements
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    /**
     *
     * @param news
     */
    @Override
    public void onShare(News news) {
        Uri image = Uri.parse(news.getUrlToImage());
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle() + "  " + news.getDescription() + "  " + image);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    /**
     *
     * @param news
     */
    @Override
    public void onSelect(News news) {

        model.setSelected(news);
        Fragment fragment = new NewsSingleFragment();
        replaceFragment(fragment);
    }

    /**
     *
     * @param news
     * @param isliked
     */
    @Override
    public void onLike(News news, boolean isliked) {
        model.removeFav(news);

    }

    /**
     *
     * @param someFragment
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
