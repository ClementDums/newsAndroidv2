package com.clt.dumas.clem.news.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Comments")
public class Comments implements Parcelable {
    @PrimaryKey
    @androidx.annotation.NonNull
    private int id;
    private String author;
    private String content;

    // CONSTRUCTOR

    public Comments(@NonNull int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    // GETTERS AND SETTERS

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

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
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

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
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
    }

    protected Comments(Parcel in) {
        id = in.readInt();
        author = in.readString();
        content = in.readString();
    }
}


