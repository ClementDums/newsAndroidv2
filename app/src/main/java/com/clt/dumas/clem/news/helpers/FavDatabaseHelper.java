package com.clt.dumas.clem.news.helpers;

import android.content.Context;

import com.clt.dumas.clem.news.database.FavDatabase;

import androidx.room.Room;
import bolts.Task;

public class FavDatabaseHelper {
    private static FavDatabase database;

    public static void init(Context context) {

        Task.callInBackground(() -> {
            database = Room.databaseBuilder(context, FavDatabase.class, "fav-db").build();
            return null;
        }).continueWith(task -> null, Task.UI_THREAD_EXECUTOR);

    }

    public static FavDatabase getDatabase() {
        return database;
    }
}
