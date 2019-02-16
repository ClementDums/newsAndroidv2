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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SavedNews news);

    @Query("DELETE FROM saved WHERE news_title = :title")
    void removeByTitle(String title);

    @Query("SELECT news.* FROM news, saved " +
            "WHERE news.title == saved.news_title")
    List<News> getAllSaved();

}
