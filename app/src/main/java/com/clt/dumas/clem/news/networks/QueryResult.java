package com.clt.dumas.clem.news.networks;

import com.clt.dumas.clem.news.model.News;

import java.util.List;

public class QueryResult {
    private List<News> articles;
    private String status;
    private int totalResults;

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public QueryResult(List<News> articles, String status, int totalResults) {
        this.articles = articles;
        this.status = status;
        this.totalResults = totalResults;
    }


}
