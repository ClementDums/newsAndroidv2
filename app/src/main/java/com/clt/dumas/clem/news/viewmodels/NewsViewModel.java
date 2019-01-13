package com.clt.dumas.clem.news.viewmodels;

import android.support.annotation.NonNull;

import com.clt.dumas.clem.news.BuildConfig;
import com.clt.dumas.clem.news.MainActivity;
import com.clt.dumas.clem.news.QueryResult;
import com.clt.dumas.clem.news.database.NewsDatabase;
import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.helpers.InternetStatusHelper;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.networks.ApikeyService;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import bolts.Continuation;
import bolts.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<News>> newsLiveData;
    private MutableLiveData<News> selected = new MutableLiveData<>();


    public void setSelected(News news) {
        selected.setValue(news);
    }


    public LiveData<News> getSelected() {
        return selected;
    }


    public LiveData<List<News>> getnews() {

        if (newsLiveData == null) {
            newsLiveData = new MutableLiveData<>();
            loadNews();
        }
        return newsLiveData;
    }

    void loadNews() {
        if (!InternetStatusHelper.isOnLine()) {
            loadNewsDb();
            return;
        }
        loadNewsApi();
    }

    /**Load news from API**/
    private void loadNewsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build();

        ApikeyService service = retrofit.create(ApikeyService.class);
        final Call<QueryResult> repos = service.listRepos("us", BuildConfig.ApiKey);

        repos.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(@NonNull Call<QueryResult> call, @NonNull Response<QueryResult> response) {

                List<News> news = null;
                if (response.body() != null) {
                    news = response.body().getArticles();
                }

                newsLiveData.setValue(news);
                insertDb(newsLiveData.getValue());
                loadNewsDb();
            }


            @Override
            public void onFailure(@NonNull Call<QueryResult> call, @NonNull Throwable t) {
                System.out.println("REC ERR -" + t.getLocalizedMessage());
            }
        });

    }

    /**Insert news in database**/
    public void insertDb(final List<News> newsList) {
        Task.callInBackground(new Callable<Object>() {
            public List<News> call() {
                DatabaseHelper.getDatabase().newsDao().insertAll(newsList);
                return null;
            }
        }).continueWith(new Continuation<Object, Object>() {

            @Override
            public Void then(Task<Object> task) throws Exception {
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }


    /**Load news from database**/
    public void loadNewsDb() {
        Task.callInBackground(new Callable<Object>() {
            public List<News> call() {
                List<News> news = DatabaseHelper.getDatabase().newsDao().getAll();
                newsLiveData.postValue(news);
                return news;
            }
        }).continueWith(new Continuation<Object, Object>() {

            @Override
            public Void then(Task<Object> task) throws Exception {
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }


    private void emptyDb() {

        Task.callInBackground(() -> {
            DatabaseHelper.getDatabase().newsDao().nukeTable();
            return null;
        });
    }
}