package com.clt.dumas.clem.news.database.daos;

import com.clt.dumas.clem.news.model.News;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<News> getAll();

//@eamosse enlevé le code mort

//    @Query("SELECT * FROM news WHERE id = :id")
//    News findByIds(int[] ids);

    @Insert
    void insertAll(List<News> news);

    @Insert
    void insertFav(News news);

    @Query("DELETE FROM news")
     void nukeTable();

    @Query("DELETE FROM news WHERE id = :id")
    void removeById(int id);

    @Query("UPDATE news SET `like` = 1 WHERE id = :id")
    void updateById(int id);
}
