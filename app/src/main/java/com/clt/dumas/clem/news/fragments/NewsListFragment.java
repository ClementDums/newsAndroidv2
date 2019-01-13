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
import com.clt.dumas.clem.news.database.NewsDatabase;
import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import bolts.Continuation;
import bolts.Task;

public class NewsListFragment extends Fragment implements NewsListener {
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;
    private NewsViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creer nouveau viewmodel ou charger un existant
        model= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NewsViewModel.class);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        init(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getnews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> newsList) {
                adapter.setNewsList(newsList);
                adapter.notifyDataSetChanged();
            }
        });
    }



    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NewsAdapter(newsList, this);
        //Associer adapteur et orientation elements
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        saveNews(newsList);
    }


    public void saveNews(final List<News> newsList){
        Task.callInBackground(new Callable<Object>() {
            public List<News> call(){
                NewsDatabase db = Room.databaseBuilder(getContext(),NewsDatabase.class, "news-db").build();
                db.newsDao().insertAll(newsList);
                List<News> news = db.newsDao().getAll();
                System.out.println("eee"+news);
                return news;
            }
        }).continueWith(new Continuation<Object, Object>() {

            @Override
            public Void then(Task<Object> task) throws Exception {
                return null;
            }
        },Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void onShare(News news) {
        Uri image = Uri.parse(news.getUrlToImage());
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle() + "  " + news.getDescription() + "  " + image);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onSelect(News news) {

        model.setSelected(news);
        Fragment fragment = new NewsSingleFragment();
        replaceFragment(fragment);
    }


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
