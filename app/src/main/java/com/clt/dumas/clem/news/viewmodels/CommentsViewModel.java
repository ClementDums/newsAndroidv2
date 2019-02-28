package com.clt.dumas.clem.news.viewmodels;

import com.clt.dumas.clem.news.model.Comments;
import com.clt.dumas.clem.news.helpers.DatabaseHelper;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import bolts.Task;

public class CommentsViewModel extends ViewModel {
    private MutableLiveData<List<Comments>> commentsLiveData;
    private MutableLiveData<Comments> selected = new MutableLiveData<>();

    public void setSelected(Comments comments) {
        selected.setValue(comments);
    }

    public LiveData<Comments> getSelected() {
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
    public void insertDb(final Comments comment) {
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
            List<Comments> comments = DatabaseHelper.getDatabase().commentDao().getAll();
            commentsLiveData.postValue(comments);
            return comments;
        });
    }
}