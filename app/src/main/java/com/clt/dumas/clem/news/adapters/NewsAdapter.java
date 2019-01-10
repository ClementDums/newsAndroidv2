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
    NewsListener listener;
    public NewsAdapter(List<News> newsList, NewsListener listener) {

        this.newsList = newsList;
        this.listener=listener;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_list_item, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            description= itemView.findViewById(R.id.description);
            image=itemView.findViewById(R.id.imageNews);
            share=itemView.findViewById(R.id.share);
        }

        public void bind(final News news) {

            title.setText(news.getTitle());
            description.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).fit().centerCrop().into(image);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onShare(news);
                }
            });
        }
    }
}
