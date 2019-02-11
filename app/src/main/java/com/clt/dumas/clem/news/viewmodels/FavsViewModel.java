package com.clt.dumas.clem.news.viewmodels;

import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.helpers.FavDatabaseHelper;
import com.clt.dumas.clem.news.model.News;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import bolts.Continuation;
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
    public void loadFavs() {
        Task.callInBackground(new Callable<Object>() {
            public List<News> call() {
                List<News> news = FavDatabaseHelper.getDatabase().newsDao().getAll();

                //Reverse list order
                Collections.reverse(news);

                favsLiveData.postValue(news);
                return news;
            }
        }).continueWith(new Continuation<Object, Object>() {

            @Override
            public Void then(Task<Object> task) throws Exception {
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
    public  void removeFav(News news){

        //@eamosse utilise les expressions lambda
        Task.callInBackground(new Callable<Object>() {
            public List<News> call() {
                FavDatabaseHelper.getDatabase().newsDao().removeById(news.getId());
                return null;
            }
        }).continueWith(new Continuation<Object, Object>() {

            @Override
            public Void then(Task<Object> task) throws Exception {
                loadFavs();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
