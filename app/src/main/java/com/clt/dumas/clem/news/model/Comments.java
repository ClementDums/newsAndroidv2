package com.clt.dumas.clem.news.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.annotation.NonNull;
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
        tableName = "Comments"
)
public class Comments implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    private String author;
    private String content;
    @ColumnInfo(name = "news_title")
    @androidx.annotation.NonNull
    private String news_title;

    // CONSTRUCTOR

    public Comments(int id, String author, String content, @NonNull String news_title) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.news_title = news_title;
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel in) {
            return new Comments(in);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public @NonNull String getNews_title() {
        return news_title;
    }

    public void setNews_title(@NonNull String news_title) {
        this.news_title = news_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(news_title);
    }

    protected Comments(Parcel in) {
        id = in.readInt();
        author = in.readString();
        content = in.readString();
        news_title = Objects.requireNonNull(in.readString());
    }
}


