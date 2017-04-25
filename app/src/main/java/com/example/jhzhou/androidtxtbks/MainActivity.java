package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jhzhou.androidtxtbks.Book;
import com.example.jhzhou.androidtxtbks.ResultReceiverWrapper;
import com.example.jhzhou.androidtxtbks.RestMethods;
import com.example.jhzhou.androidtxtbks.Service;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        
        Intent intent = new Intent(this, Service.class);
        intent.putExtra(Service.RECEIVER_KEY, receiver);
        startService(intent);
    }
    
    public void onReceiveResult(int resultCode, Bundle data){
        ArrayList<Book> books = data.getParcelableArrayList(Service.BOOK_KEY);
        this.books = books;
    }
}
