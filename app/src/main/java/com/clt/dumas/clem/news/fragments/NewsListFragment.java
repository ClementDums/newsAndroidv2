package com.clt.dumas.clem.news.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clt.dumas.clem.news.BuildConfig;
import com.clt.dumas.clem.news.QueryResult;
import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.adapters.NewsAdapter;
import com.clt.dumas.clem.news.constants.Constants;
import com.clt.dumas.clem.news.database.NewsDatabase;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.networks.ApikeyService;
import com.clt.dumas.clem.news.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import bolts.Continuation;
import bolts.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListFragment extends Fragment implements NewsListener {
    NewsViewModel viewModel = new NewsViewModel();
    private List<News> newsList = new ArrayList<>();
    NewsAdapter adapter;
    private NewsViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//creer nouveau viewmodel ou charger un existant
        model= ViewModelProviders.of(this).get(NewsViewModel.class);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        init(view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getnews().observe(this, newsList->{
            adapter.setNewsList(newsList);
            adapter.notifyDataSetChanged();

            saveNews(newsList);
        });
    }


    public void saveNews(final List<News> newsList){
        Task.callInBackground(new Callable<Object>() {
            public List<News> call(){
                NewsDatabase db = Room.databaseBuilder(getContext(),NewsDatabase.class, "news-db").build();
                db.newsDao().insertAll(newsList);
                List<News> news = db.newsDao().getAll();

                return news;
            }
        }).continueWith(new Continuation<Object, Object>() {

            @Override
            public Void then(Task<Object> task) throws Exception {
                return null;
            }
        },Task.UI_THREAD_EXECUTOR);
    }

    private void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new NewsAdapter(newsList, (NewsListener) this);
        //Associer adapteur et orientation elements
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
        Fragment fragment = new NewsSingleFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable( "myNews" , news);
        fragment.setArguments(arguments);
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = null;
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        }
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
