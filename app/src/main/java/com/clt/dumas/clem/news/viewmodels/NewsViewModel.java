package com.clt.dumas.clem.news.viewmodels;

import android.support.annotation.NonNull;

import com.clt.dumas.clem.news.model.SavedNews;
import com.clt.dumas.clem.news.networks.QueryResult;
import com.clt.dumas.clem.news.constants.Constants;
import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.helpers.InternetStatusHelper;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.networks.ApikeyService;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
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

    /**
     * @return
     */
    public LiveData<List<News>> getnews() {
        if (newsLiveData == null) {
            newsLiveData = new MutableLiveData<>();
            loadNews();
        }
        return newsLiveData;
    }

    /**
     *
     */
    private void loadNews() {
        if (!InternetStatusHelper.isOnline()) {
            loadNewsDb();
            return;
        }
        loadNewsApi();
    }

    /**
     * Load news from API
     **/
    private void loadNewsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build();

        ApikeyService service = retrofit.create(ApikeyService.class);
        final Call<QueryResult> repos = service.listRepos("us", Constants.getApiKey());

        repos.enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(@NonNull Call<QueryResult> call, @NonNull Response<QueryResult> response) {
                List<News> news = null;
                if (response.body() != null) {
                    news = response.body().getArticles();
                }
                //Insert if not already in database
                insertDb(news);
                //Load other news from db
                loadNewsDb();

            }

            @Override
            public void onFailure(@NonNull Call<QueryResult> call, @NonNull Throwable t) {
                System.out.println("REC ERR -" + t.getLocalizedMessage());
            }
        });
    }

    /**
     * @param newsList
     */
    //Insert news in database
    private void insertDb(final List<News> newsList) {
        Task.callInBackground(() -> {
            DatabaseHelper.getDatabase().newsDao().insertAll(newsList);
            return null;
        }).continueWith(task -> {
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * Load news from database
     **/

    public void loadNewsDb() {
        Task.callInBackground((Callable<Object>) () -> {
            List<News> news = DatabaseHelper.getDatabase().newsDao().getAll();
            newsLiveData.postValue(news);
            return news;
        });
    }

    /**
     * @param news
     * @param isLiked
     */
    public void setFav(News news, boolean isLiked) {
        if (!isLiked) {
            addFav(news);
            return;
        }
        removetoFav(news);
    }

    /**
     * @param news
     */
    private void addFav(News news) {
        news.setLike(true);
        addtoFav(news);
        newsSetLike(news);
    }

    private void removetoFav(News news) {
        news.setLike(false);
        removeFav(news);
        newsRemoveLike(news);
    }

    private void newsSetLike(News news) {
        Task.callInBackground(() -> {
            String myTitle = news.getTitle();
            DatabaseHelper.getDatabase().newsDao().setLike(myTitle);
            return null;
        });
    }

    private void newsRemoveLike(News news) {
        Task.callInBackground(() -> {
            String myTitle = news.getTitle();
            DatabaseHelper.getDatabase().newsDao().removeLike(myTitle);
            return null;
        });
    }

    private void addtoFav(News news) {
        Task.callInBackground(() -> {
            SavedNews savedNews = new SavedNews(news.getTitle());
            DatabaseHelper.getDatabase().savedDao().insert(savedNews);
            return null;
        });
    }

    /**
     * @param news
     */
    private void removeFav(News news) {
        Task.callInBackground(() -> {
            news.setLike(false);
            DatabaseHelper.getDatabase().savedDao().removeByTitle(news.getTitle());
            return null;
        });
    }
}