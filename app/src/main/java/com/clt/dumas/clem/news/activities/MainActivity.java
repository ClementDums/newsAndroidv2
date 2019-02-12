package com.clt.dumas.clem.news.activities;

import android.os.Bundle;

import com.clt.dumas.clem.news.R;
import com.clt.dumas.clem.news.helpers.DatabaseHelper;
import com.clt.dumas.clem.news.fragments.NewsListFragment;
import com.clt.dumas.clem.news.helpers.InternetStatusHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init Internet
        InternetStatusHelper.init(this);
        //Init Database
        DatabaseHelper.init(this);
        //CALL FRAGMENTS
        //Create fragment instance
        NewsListFragment fragment = new NewsListFragment();
        //Create instance
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //Replace container content
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        //Valid transaction
        transaction.commit();
    }
}
