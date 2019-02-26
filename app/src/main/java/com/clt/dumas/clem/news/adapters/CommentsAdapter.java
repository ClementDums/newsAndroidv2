package com.clt.dumas.clem.news.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.Comments;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private List<Comments> commentsList;
    private NewsListener listener;

    /**
     * @param commentsList
     * @param listener
     */
    public CommentsAdapter(List<Comments> commentsList, NewsListener listener) {
        this.commentsList = commentsList;
        this.listener = listener;
    }

    /**
     * @param commentsList
     */
    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.comment_list_fragment, viewGroup, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        CommentsViewHolder.bind(commentsList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;

        CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id);
            content = itemView.findViewById(R.id);
        }

        void bind(final Comments comments) {
            author.setText(comments.getAuthor());
            content.setText(comments.getContent());
        }
    }
}
