package com.clt.dumas.clem.news.database.daos;

import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.model.SavedNews;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SavedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedNews news);


    @Query("DELETE FROM saved WHERE news_id = :id")
    void removeById(int id);

    @Query("SELECT news.* FROM news, saved " +
            "WHERE news.id == saved.news_id")

    List<News> getAllSaved();

}
