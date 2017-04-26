package com.example.jhzhou.androidtxtbks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/24/17.
 */

public class HomeFragment extends Fragment implements BookAdapter.OnListItemClickedListener, ResultReceiverWrapper.IReceive {

    private BookAdapter mAdapter;
    private ResultReceiverWrapper resultReceiver;

    @BindView(R.id.home_fragment_recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.home_search_image) ImageView searchImageView;
    @BindView(R.id.home_fragment_progress_bar) ProgressBar mProgressBar;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultReceiver = new ResultReceiverWrapper(new Handler());
    }

    @Override
    public void onResume() {
        super.onResume();
        resultReceiver.setReceiver(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        resultReceiver.setReceiver(null);
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
                mProgressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getContext(), Service.class);
                intent.putExtra(Service.RECEIVER_KEY, resultReceiver);
                getContext().startService(intent);

//                List<Book> bookList = new ArrayList<Book>();
//                Book booktemp = new Book();
//                booktemp.title="chemstry";
//                booktemp.authors = new ArrayList<String>();
//                booktemp.authors.add("Jiahuang");
//                booktemp.googlePrice = 123.23;
//                booktemp.isbn10 = "123456";
//                booktemp.isbn13 = "123456";
//                bookList.add(booktemp);
//
//                mAdapter.setBookList(bookList);
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

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        ArrayList<Book> books = data.getParcelableArrayList(Service.BOOK_KEY);
        List<Book> removingBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.googlePrice == 0)
                removingBooks.add(book);
        }
        books.removeAll(removingBooks);
        mAdapter.setBookList(books);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
