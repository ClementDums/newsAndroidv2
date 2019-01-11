package com.clt.dumas.clem.news.viewmodels;

import android.content.Context;
import android.net.ConnectivityManager;

import com.clt.dumas.clem.news.BuildConfig;
import com.clt.dumas.clem.news.QueryResult;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.networks.ApikeyService;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<News>> newsLiveData;
    private MutableLiveData<News> selected = new MutableLiveData<>();

    public LiveData<List<News>> getnews() {
        if (newsLiveData == null) {
            newsLiveData = new MutableLiveData<List<News>>();

            loadNews();
        }
        return newsLiveData;
    }

    private void loadNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build();

        ApikeyService service = retrofit.create(ApikeyService.class);
        final Call<QueryResult> repos = service.listRepos("us", BuildConfig.ApiKey);

        repos.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {

                List<News> news = response.body().getArticles();

                newsLiveData.setValue(news);
            }


            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                System.out.println("REC ERR -" + t.getLocalizedMessage());
            }
        });

    }

    public void setSelected(News news){
        selected.setValue(news);
    }

    public LiveData<News> getSelected(){
        return selected;
    }
}