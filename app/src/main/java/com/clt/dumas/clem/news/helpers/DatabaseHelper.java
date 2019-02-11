package com.clt.dumas.clem.news.helpers;

import android.content.Context;

import com.clt.dumas.clem.news.database.NewsDatabase;

import androidx.room.Room;
import bolts.Task;


/**
 * @eamosse
 * 1° Commentaires - dis ce que fait la classe
 */
public class DatabaseHelper {
    private static NewsDatabase database;
//    TABLE NAMES
    private static final String TABLE_NEWS = "news";
    private static final String TABLE_FAVS = "favs";
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
