package com.clt.dumas.clem.news.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.listeners.NewsListener;
import com.clt.dumas.clem.news.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList;
    private NewsListener listener;

    /**
     * @param newsList
     * @param listener
     */
    public NewsAdapter(List<News> newsList, NewsListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    /**
     * @param newsList
     */
    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    /**
     * @param viewGroup
     * @param position
     * @return
     */
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_list_item, viewGroup, false);
        return new NewsViewHolder(view);
    }

    /**
     * @param NewsViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder NewsViewHolder, int position) {
        NewsViewHolder.bind(newsList.get(position));
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
        ImageView share;
        ImageView like;
        ImageView comments;

        /**
         * @param itemView
         */
        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageNews);
            share = itemView.findViewById(R.id.share);
            like = itemView.findViewById(R.id.like);
            comments = itemView.findViewById(R.id.comments);
        }

        /**
         * Set news Content and onClick
         *
         * @param news
         */
        void bind(final News news) {
            //Set Content
            title.setText(news.getTitle());
            description.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).fit().centerCrop().into(image);
            //share onClick
            share.setOnClickListener(v -> listener.onShare(news));
            //news onClick
            itemView.setOnClickListener(v -> listener.onSelect(news));
            //like onClick
            if (news.isLike()) {
                like.setImageResource(R.drawable.ic_like_true);
            } else {
                like.setImageResource(R.drawable.ic_action_name);
            }

            like.setOnClickListener(v -> {
                boolean isLiked = news.isLike();
                //if not liked
                if (!isLiked) {
                    like.setImageResource(R.drawable.ic_like_true);
                } else {
                    //if is liked
                    like.setImageResource(R.drawable.ic_action_name);
                }
                //call listener onlike
                listener.onLike(news, isLiked);
            });

            comments.setOnClickListener(v -> listener.onComment(news));
        }
    }
}
