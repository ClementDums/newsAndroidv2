package com.clt.dumas.clem.news.database;

import com.clt.dumas.clem.news.database.daos.NewsDao;
import com.clt.dumas.clem.news.database.daos.SavedDao;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.model.SavedNews;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {News.class,SavedNews.class},version = 2)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
    public abstract SavedDao savedDao();
}
