package com.clt.dumas.clem.news.viewmodels;

import android.support.annotation.NonNull;

import com.clt.dumas.clem.news.model.Comments;
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
import bolts.Continuation;
import bolts.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @eamosse 1° Commentaires (classe et méthode)
 * 2° attention aux espaces en trop entre les lignes
 * 3° utilise les expressions lambda
 */
public class CommentsViewModel extends ViewModel {
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
    public LiveData<List<Comments>> getComments() {
        if (commentsLiveData == null) {
            commentsLiveData = new MutableLiveData<>();
            loadCommentsDB();
        }
        return commentsLiveData;
    }

    /**
     * @param comment
     */
    // Insert a comment in database
    private void insertDb(final Comments comment) {
        Task.callInBackground(() -> {
            DatabaseHelper.getDatabase().commentDao().insert(comment);
            return null;
        }).continueWith(task -> {
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * Load comments from database
     **/

    public void loadCommentsDB() {
        Task.callInBackground((Callable<Object>) () -> {
            List<News> news = DatabaseHelper.getDatabase().newsDao().getAll();
            newsLiveData.postValue(news);
            return news;
        });
    }
}