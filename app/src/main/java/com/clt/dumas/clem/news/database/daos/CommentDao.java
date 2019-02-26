package com.clt.dumas.clem.news.database.daos;

import com.clt.dumas.clem.news.model.Comments;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comments")
    List<Comments>getAll();

    @Insert
    void insert(Comments comment);
}
