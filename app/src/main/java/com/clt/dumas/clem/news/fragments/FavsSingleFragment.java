package com.clt.dumas.clem.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.viewmodels.FavsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
//Favourite single fragment
public class FavsSingleFragment extends Fragment {
    private FavsViewModel model;
    private TextView titleTv;
    private TextView descriptionTv;
    private ImageView image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get Favs view model
        model= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout
        View view = inflater.inflate(R.layout.news_single_fragment, container, false);
        //get fields
         titleTv =view.findViewById(R.id.name);
         descriptionTv = view.findViewById(R.id.description);
         image= view.findViewById(R.id.imageSingle);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.getSelected().observe(this,news -> {
            //set fields
            titleTv.setText(news.getTitle());
            descriptionTv.setText(news.getDescription());
            Picasso.get().load(news.getUrlToImage()).fit().centerCrop().into(image);
        });
    }

}
