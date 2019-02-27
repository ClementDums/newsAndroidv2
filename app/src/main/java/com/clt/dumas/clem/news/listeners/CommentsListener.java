package com.clt.dumas.clem.news.listeners;

import com.clt.dumas.clem.news.model.Comments;

public interface CommentsListener {
    void onAdd(Comments comment);
    void onRemove(Comments comment);
    void onSelect(Comments comment);
}
