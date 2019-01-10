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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsSingleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_single_fragment, container, false);

        Bundle arguments = getArguments();
        News news = arguments.getParcelable("myNews");

        String titleContent = news.getTitle();
        String descriptionContent = news.getDescription();
        String imageUrl = news.getUrlToImage();

        TextView titleTv =view.findViewById(R.id.name);
        TextView descriptionTv = view.findViewById(R.id.description);
        ImageView image= view.findViewById(R.id.imageSingle);

        titleTv.setText(titleContent);
        descriptionTv.setText(descriptionContent);
        Picasso.get().load(imageUrl).into(image);


        return view;
    }
}
