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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<News> newsList;
    private NewsListener listener;

    public NewsAdapter(List<News> newsList, NewsListener listener) {

        this.newsList = newsList;
        this.listener = listener;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.bind(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
        ImageView share;
        ImageView like;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageNews);
            share = itemView.findViewById(R.id.share);
            like = itemView.findViewById(R.id.like);
        }

        void bind(final News news) {

            title.setText(news.getTitle());
            description.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).fit().centerCrop().into(image);

            share.setOnClickListener(v -> listener.onShare(news));

            itemView.setOnClickListener(v -> listener.onSelect(news));
            if (news.isLike()) {
                like.setImageResource(R.drawable.ic_like_true);
            } else {
                like.setImageResource(R.drawable.ic_action_name);
            }

            like.setOnClickListener(v -> {
                if (!news.isLike()) {
                    news.setLike(true);
                    like.setImageResource(R.drawable.ic_like_true);

                } else {
                    news.setLike(false);
                    like.setImageResource(R.drawable.ic_action_name);
                }
            });

        }
    }
}
