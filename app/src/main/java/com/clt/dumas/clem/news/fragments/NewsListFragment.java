package com.clt.dumas.clem.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clt.dumas.clem.news.BuildConfig;
import com.clt.dumas.clem.news.QueryResult;
import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.adapters.NewsAdapter;
import com.clt.dumas.clem.news.constants.Constants;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.networks.ApikeyService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListFragment extends Fragment {

    private List<News> NewsList = new ArrayList<>();
    NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loadNews();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        init(view);

        return view;
    }


    public void loadNews(){

        Constants constants = new Constants();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build();

        ApikeyService service = retrofit.create(ApikeyService.class);
        final Call<QueryResult> repos = service.listRepos("us",BuildConfig.ApiKey);

        repos.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {


                NewsList= response.body().getArticles();
                adapter.setNewsList(NewsList);
                  adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                System.out.println("REC ERR -" +t.getLocalizedMessage());
            }
        });

    }


    public void init(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter= new NewsAdapter(NewsList);
        //Associer adapteur et orientation elements
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
