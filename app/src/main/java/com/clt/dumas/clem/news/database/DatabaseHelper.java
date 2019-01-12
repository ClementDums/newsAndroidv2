package com.clt.dumas.clem.news.database;

import android.content.Context;
import androidx.room.Room;
import bolts.Task;



public class DatabaseHelper {
    private static NewsDatabase database;
    public static void init(Context context){

        Task.callInBackground(() -> {
            database = Room.databaseBuilder(context,NewsDatabase.class, "news-db").build();
            return null;
        }).continueWith(task -> null,Task.UI_THREAD_EXECUTOR);

    }
    public static NewsDatabase getDatabase(){
        return database;
    }
}
