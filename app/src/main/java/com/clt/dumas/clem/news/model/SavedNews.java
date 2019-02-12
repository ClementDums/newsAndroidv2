package com.clt.dumas.clem.news.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = News.class,
        parentColumns = "id",
        childColumns = "news_id"),
        indices = {@Index(value = "news_id", unique = true)},
        tableName = "Saved"
)
public class SavedNews {
    @PrimaryKey
    @ColumnInfo(name = "news_id")
    private final int newsId;
    private boolean like =true;

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isLike() {
        return like;
    }

    public SavedNews(int newsId) {
        this.newsId = newsId;
    }

    public int getNewsId() {
        return newsId;
    }
}
