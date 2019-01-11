package com.clt.dumas.clem.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.viewmodels.NewsViewModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class NewsSingleFragment extends Fragment {
    private NewsViewModel model;
    TextView titleTv;
    TextView descriptionTv;
    ImageView image;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= ViewModelProviders.of(getActivity()).get(NewsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_single_fragment, container, false);

         titleTv =view.findViewById(R.id.name);
         descriptionTv = view.findViewById(R.id.description);
         image= view.findViewById(R.id.imageSingle);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getSelected().observe(this,news -> {
            titleTv.setText(news.getTitle());
            descriptionTv.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).into(image);
        });
    }

}
