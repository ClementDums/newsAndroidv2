package com.clt.dumas.clem.news.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "News")
public class News implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private boolean like =false;
    private String title;
    private String description;
    private String urlToImage;
    private String publishedAt;
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @param title
     * @param description
     * @param urlToImage
     * @param publishedAt
     */
    public News(String title, String description, String urlToImage, String publishedAt) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", like="+like+
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.urlToImage);
    }

    protected News(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.urlToImage = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
