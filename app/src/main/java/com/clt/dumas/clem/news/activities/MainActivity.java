package com.clt.dumas.clem.news.activities;

import android.os.Bundle;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.fragments.NewsListFragment;
import com.clt.dumas.clem.news.helpers.FavDatabaseHelper;
import com.clt.dumas.clem.news.helpers.InternetStatusHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * @eamosse
 * 1° commente ta classe
 * 2° attention aux espaces en trop entre les instructions
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InternetStatusHelper.init(this);
        DatabaseHelper.init(this);
        FavDatabaseHelper.init(this);

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
