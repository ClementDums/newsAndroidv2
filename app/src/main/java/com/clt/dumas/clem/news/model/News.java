package com.clt.dumas.clem.news.model;

public class News {
    String title;
    String description;
    String urlToImage;

    public News(String title, String description, String urlToImage) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
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


    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                '}';
    }
}
