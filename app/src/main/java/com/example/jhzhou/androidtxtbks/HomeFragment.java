package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/24/17.
 */

public class HomeFragment extends Fragment implements BookAdapter.OnListItemClickedListener{

    private BookAdapter mAdapter;

    @BindView(R.id.home_fragment_recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.home_search_image) ImageView searchImageView;

    public static  HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new BookAdapter(getActivity(), new ArrayList<Book>(), this);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("searchImageView", "click");
                List<Book> bookList = new ArrayList<Book>();
                Book booktemp = new Book();
                booktemp.title="chemstry";
                booktemp.authors = new ArrayList<String>();
                booktemp.authors.add("Jiahuang");
                booktemp.googlePrice = 123.23;
                booktemp.isbn10 = "123456";
                booktemp.isbn13 = "123456";
                bookList.add(booktemp);

                mAdapter.setBookList(bookList);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClicked(Book book) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        DetailsFragment fragment = DetailsFragment.getInstance(book);
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
}
