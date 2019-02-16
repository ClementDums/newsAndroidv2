package com.clt.dumas.clem.news.model;

import android.support.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = News.class,
        parentColumns = "title",
        childColumns = "news_title"),
        indices = {@Index(value = "news_title", unique = true)},
        tableName = "Saved"
)
public class SavedNews {
    @PrimaryKey
    @ColumnInfo(name = "news_title")
    @androidx.annotation.NonNull
    private final String newsTitle;
    private boolean like =true;

    public boolean isLike() {
        return like;
    }

    @NonNull
    public String getNewsTitle() {
        return newsTitle;
    }

    public SavedNews(@NonNull String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
