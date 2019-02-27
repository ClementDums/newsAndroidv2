package com.clt.dumas.clem.news.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.listeners.CommentsListener;
import com.clt.dumas.clem.news.model.Comments;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private List<Comments> commentsList;
    private CommentsListener listener;

    /**
     * @param commentsList
     */
    public CommentsAdapter(List<Comments> commentsList, CommentsListener listener) {
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
        holder.bind(commentsList.get(position));
    }

    @Override
    public int getItemCount() {
        if(commentsList != null) return commentsList.size();
        else return 0;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;
        Button add;

        CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
            add = itemView.findViewById(R.id.addcomment);
        }

        void bind(Comments comment) {
            author.setText(comment.getAuthor());
            content.setText(comment.getContent());

            add.setOnClickListener(v -> {
                listener.onAdd(comment);
            });
            itemView.setOnClickListener(v -> {
                listener.onSelect(comment);
            });
        }
    }
}
