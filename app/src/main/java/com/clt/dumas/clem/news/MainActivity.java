package com.clt.dumas.clem.news;

import android.os.Bundle;
import android.widget.Toast;

import com.clt.dumas.clem.news.constants.Constants;
import com.clt.dumas.clem.news.fragments.NewsListFragment;
import com.clt.dumas.clem.news.model.News;
import com.clt.dumas.clem.news.networks.ApikeyService;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        //CALL FRAGMENTS

        //Créer une instance du fragment
        NewsListFragment fragment = new NewsListFragment();
        //Créer une transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //Remplacer contenu du container
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        //valider transaction
        transaction.commit();
    }
}
