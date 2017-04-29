package com.example.jhzhou.androidtxtbks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SubjectFragment.OnListItemClickListener {

    List<Book> books;
    ResultReceiverWrapper receiver;

    HomeFragment mFragmentHome;
    SearchFragment mFragmentSearch;
    SubjectFragment mFragmentSubject;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = mFragmentHome;
                    break;
                case R.id.navigation_search:
                    selectedFragment = mFragmentSearch;
                    break;
                case R.id.navigation_subject:
                    selectedFragment = mFragmentSubject;
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MainAcitvity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            mFragmentHome = HomeFragment.getInstance();
            mFragmentSearch = SearchFragment.getInstance();
            mFragmentSubject = SubjectFragment.getInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, mFragmentHome);
            transaction.commit();
        }
    }

    @Override
    public void onClickListener(String searchKey) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, mFragmentHome);
        transaction.commit();

        mFragmentHome.setmSearchKey(searchKey);
        mFragmentHome.startSearch();
    }

//    public void onReceiveResult(int resultCode, Bundle data){
//        ArrayList<Book> books = data.getParcelableArrayList(Service.BOOK_KEY);
//        this.books = books;
//    }
}
