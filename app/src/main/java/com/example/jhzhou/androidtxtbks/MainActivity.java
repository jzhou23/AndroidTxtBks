package com.example.jhzhou.androidtxtbks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    ArrayList<Book> books;
    ResultReceiverWrapper receiver;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.getInstance();
                    break;
                case R.id.navigation_search:
                    selectedFragment = SearchFragment.getInstance();
                    break;
                case R.id.navigation_subject:
                    selectedFragment = SubjectFragment.getInstance();
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        books = new ArrayList<>();
        receiver = new ResultReceiverWrapper(new Handler());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, HomeFragment.getInstance());
        transaction.commit();
        
//        Intent intent = new Intent(this, Service.class);
//        intent.putExtra(Service.RECEIVER_KEY, receiver);
//        startService(intent);
    }
    
    public void onReceiveResult(int resultCode, Bundle data){
        ArrayList<Book> books = data.getParcelableArrayList(Service.BOOK_KEY);
        this.books = books;
    }
}
