package com.clt.dumas.clem.news.database.daos;

import com.clt.dumas.clem.news.model.News;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Insert
    void insertAll(List<News> news);

    @Query("UPDATE news SET `like` = 1 WHERE title = :title")
    void setLike(String title);

    @Query("UPDATE news SET `like` = 0 WHERE title = :title")
    void removeLike(String title);
}
