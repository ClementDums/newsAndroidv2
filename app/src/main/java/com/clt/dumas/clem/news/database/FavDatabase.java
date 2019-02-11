package com.clt.dumas.clem.news.database;

import com.clt.dumas.clem.news.database.daos.NewsDao;
import com.clt.dumas.clem.news.model.News;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @eamosse
 * Il ne faut pas utiliser une base de données par table -
 * il y a une seule bdd pour l'appication et plusieurs tables
 * Cette classe est redondante
 */
@Database(entities = {News.class},version = 1)
public abstract class FavDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
