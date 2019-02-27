package com.clt.dumas.clem.news.viewmodels;

import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.model.SavedNews;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import bolts.Task;

public class FavsViewModel extends ViewModel {
    private MutableLiveData<List<News>> favsLiveData;
    private MutableLiveData<News> selected = new MutableLiveData<>();

    public void setSelected(News news) {
        selected.setValue(news);
    }

    public LiveData<News> getSelected() {
        return selected;
    }

    public LiveData<List<News>> getFavs() {
        if (favsLiveData == null) {
            favsLiveData = new MutableLiveData<>();
        }
        loadFavs();
        return favsLiveData;
    }
    /**Load news from database**/
    private void loadFavs() {
        Task.callInBackground((Callable<Object>) () -> {
            List<News> news = DatabaseHelper.getDatabase().savedDao().getAllSaved();
            Collections.reverse(news);
            favsLiveData.postValue(news);
            return news;
        });
    }

    public  void removeFav(News news){
        Task.callInBackground(() -> {
            news.setLike(false);
            //Remove news from saved news
            DatabaseHelper.getDatabase().savedDao().removeByTitle(news.getTitle());
            DatabaseHelper.getDatabase().newsDao().removeLike(news.getTitle());

            return null;
        }).continueWith(task -> {
            loadFavs();
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    public void setFav(News news, boolean isLiked) {
        if (!isLiked) {
            addFav(news);
            return;
        }
        removetoFav(news);
    }

    private void removetoFav(News news) {
        news.setLike(false);
        removeFav(news);
        newsRemoveLike(news);
    }

    private void newsRemoveLike(News news) {
        Task.callInBackground(() -> {
            String myTitle = news.getTitle();
            DatabaseHelper.getDatabase().newsDao().removeLike(myTitle);
            return null;
        });
    }

    private void addFav(News news) {
        news.setLike(true);
        addtoFav(news);
        newsSetLike(news);
    }

    private void newsSetLike(News news) {
        Task.callInBackground(() -> {
            String myTitle = news.getTitle();
            DatabaseHelper.getDatabase().newsDao().setLike(myTitle);
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


}
