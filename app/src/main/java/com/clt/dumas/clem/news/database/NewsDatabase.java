package com.clt.dumas.clem.news.database;

import com.clt.dumas.clem.news.database.daos.NewsDao;
import com.clt.dumas.clem.news.model.News;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {News.class},version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
