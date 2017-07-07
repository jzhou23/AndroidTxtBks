package com.example.jhzhou.androidtxtbks;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private static final String TAG_LOG = HomeFragment.class.getSimpleName();
    private static final String BOOK_LIST = "list";

    private Context mContext;

    private BookAdapter mAdapter;
    private List<Book> mBookList;
    private ResultReceiverWrapper resultReceiver;
    private String mSearchKey;

    @BindView(R.id.home_fragment_recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.home_search_image) ImageView searchImageView;
    @BindView(R.id.home_fragment_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.home_search_criteria) EditText searchCriteria;
    @BindView(R.id.home_fragment_textview) TextView textView;

    public static HomeFragment getInstance() {
        return new HomeFragment();
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
//        resultReceiver.setReceiver(null);
        // leak memory
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("searchImageView", "click");
                mSearchKey = searchCriteria.getText().toString();
                startSearch();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new BookAdapter(getActivity(), new ArrayList<Book>(), this);
        if (savedInstanceState != null) {
            mAdapter.setBookList(savedInstanceState.<Book>getParcelableArrayList(BOOK_LIST));
        }

        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClicked(Book book) {
        Intent intentDetails = new Intent(getActivity(), DetailsActivity.class);
        intentDetails.putExtra(DetailsActivity.BOOK, book);
        startActivity(intentDetails);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        Log.v("onReceiveResult", "onReceiveResult");
        mProgressBar.setVisibility(View.INVISIBLE);
        ArrayList<Book> books = data.getParcelableArrayList(Service.BOOK_KEY);
        List<Book> removingBooks = new ArrayList<>();
        for (Book book : books) {
//            if (book.googlePrice == 0)
//                removingBooks.add(book);
        }
        if (books.size() > 0) {
            showData();
            books.removeAll(removingBooks);
            setmBookList(books);
        } else {
            showError();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BOOK_LIST, (ArrayList<? extends Parcelable>) mBookList);
    }

    public void setmBookList(List<Book> bookList) {
        mBookList = bookList;
        mAdapter.setBookList(mBookList);
    }

    public void setmSearchKey(String searchKey) {
        mSearchKey = searchKey;
    }

    public void showData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }

    public void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
    }

    public void startSearch() {
        Log.v(TAG_LOG, "startSearch");
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(mContext, Service.class);
        intent.putExtra(Service.RECEIVER_KEY, resultReceiver);
        intent.putExtra(Service.SEARCH_CRITERIA, mSearchKey);
        Log.v("mSearchKey", mSearchKey);
        mContext.startService(intent);
    }
}
