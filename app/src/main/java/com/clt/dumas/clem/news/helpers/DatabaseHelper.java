package com.clt.dumas.clem.news.helpers;

import android.content.Context;

import com.clt.dumas.clem.news.database.NewsDatabase;

import androidx.room.Room;
import bolts.Task;


/**
 * Build "news-db" database
 */
public class DatabaseHelper {
    private static NewsDatabase database;
    public static void init(Context context){
        Task.callInBackground(() -> {
            database = Room.databaseBuilder(context,NewsDatabase.class, "news-db").fallbackToDestructiveMigration().build();
            return null;
        }).continueWith(task -> null,Task.UI_THREAD_EXECUTOR);
    }
    public static NewsDatabase getDatabase(){
        return database;
    }
}
