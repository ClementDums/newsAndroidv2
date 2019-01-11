package com.clt.dumas.clem.news.listeners;

import com.clt.dumas.clem.news.model.News;

public interface NewsListener {
    void onShare(News news);
    void  onSelect(News news);
    void onLike(News news);
}
