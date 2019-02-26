package com.clt.dumas.clem.news.database;


import com.clt.dumas.clem.news.database.daos.CommentDao;
import com.clt.dumas.clem.news.database.daos.SavedCommentDao;
import com.clt.dumas.clem.news.model.Comments;
import com.clt.dumas.clem.news.model.SavedComments;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Comments.class, SavedComments.class}, version = 3)
public abstract class CommentDatabase extends RoomDatabase {
    public abstract CommentDao commentDao();
    public abstract SavedCommentDao savedCommentDao();
}
