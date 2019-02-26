package com.clt.dumas.clem.news.database.daos;

import com.clt.dumas.clem.news.model.Comments;
import com.clt.dumas.clem.news.model.SavedComments;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SavedCommentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SavedComments comment);

    @Query("DELETE FROM savedComments WHERE id = :id")
    void removeById(int id);

    @Query("SELECT comments.* FROM comments, savedComments " +
            "WHERE comments.id == savedComments.id")
    List<Comments> getAllSaved();
}
