package com.example.jhzhou.androidtxtbks;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/24/17.
 */

public class SearchFragment extends Fragment implements ResultReceiverWrapper.IReceive {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String SUBJECT = "subject";

    @BindView(R.id.search_title) EditText titleEditText;
    @BindView(R.id.search_author) EditText authorEditText;
    @BindView(R.id.search_subject) EditText subjectEditText;
    @BindView(R.id.search_button) Button button;
    ResultReceiverWrapper resultReceiver;

    public static SearchFragment getInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultReceiver = new ResultReceiverWrapper(new Handler());
        resultReceiver.setReceiver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle("Bundle");
            titleEditText.setText(bundle.getString(TITLE));
            authorEditText.setText(bundle.getString(AUTHOR));
            subjectEditText.setText(bundle.getString(SUBJECT));
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "";
                String author = "";
                String subject = "";
                String criteria = "";
                if (titleEditText.getText() != null)
                    title = titleEditText.getText().toString();
                if (authorEditText.getText() != null)
                    author = authorEditText.getText().toString();
                if (subjectEditText.getText() != null)
                    subject = subjectEditText.getText().toString();

                //inauthor:Pedro+Domingos+intitle:algorithm
                if (author.length() > 0)
                    criteria += String.format("inauthor:%s+", author);
                if (title.length() > 0)
                    criteria += String.format("intitle:%s+", title);
                if (subject.length() > 0)
                    criteria += String.format("subject:%s+", subject);
                criteria = criteria.substring(0, criteria.length() - 1);

                Intent intent = new Intent(getContext(), Service.class);
                intent.putExtra(Service.SEARCH_CRITERIA, criteria.toString());
                intent.putExtra(Service.RECEIVER_KEY, resultReceiver);
                getContext().startService(intent);
            }
        });
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        ArrayList<Book> books = data.getParcelableArrayList(Service.BOOK_KEY);
         Log.v("books", String.valueOf(books.size()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, titleEditText.getText().toString());
        bundle.putString(AUTHOR, authorEditText.getText().toString());
        bundle.putString(SUBJECT, subjectEditText.getText().toString());
        outState.putBundle("Bundle", bundle);
    }
}
