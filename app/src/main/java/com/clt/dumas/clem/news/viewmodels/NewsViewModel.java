package com.clt.dumas.clem.news.viewmodels;

import com.clt.dumas.clem.news.model.News;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<News>> news;

    public LiveData<List<News>> getnews() {
        if (news == null) {
            news = new MutableLiveData<List<News>>();
            loadNews();
        }
        return news;
    }

    private void loadNews() {

    }
}