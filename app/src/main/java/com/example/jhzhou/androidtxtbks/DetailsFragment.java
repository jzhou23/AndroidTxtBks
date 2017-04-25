package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/25/17.
 */

public class DetailsFragment extends Fragment {

    public static final String BOOK = "book";

    private Book mBook;

    @BindView(R.id.details_toolbar) Toolbar mToolbar;
    @BindView(R.id.details_main_price) TextView mainPriceTextView;
    @BindView(R.id.details_penny_price) TextView pennyPriceTextView;
    @BindView(R.id.details_title) TextView titleTextView;
    @BindView(R.id.details_author) TextView authorTextView;
    @BindView(R.id.details_image_view) ImageView coverImageView;

    @BindView(R.id.details_subject_content) TextView subjectTextView;
    @BindView(R.id.details_handcover_content) TextView handCoverTextView;
    @BindView(R.id.details_publisher_content) TextView publisherTextView;
    @BindView(R.id.details_isbn_10_content) TextView isbn10TextView;
    @BindView(R.id.details_isbn_13_content) TextView isbn13TextView;

    public static DetailsFragment getInstance(Book book) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBook = getArguments().getParcelable(BOOK);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int price = (int)Math.floor(mBook.googlePrice * 100);

        mainPriceTextView.setText("$" + String.valueOf(price / 100));
        pennyPriceTextView.setText(String.valueOf(price % 100));

        titleTextView.setText(mBook.title);
        //check
        authorTextView.setText(mBook.authors.get(0));
        Glide.with(getActivity()).load(mBook.imgLink).centerCrop().into(coverImageView);

        isbn10TextView.setText(mBook.isbn10);
        isbn13TextView.setText(mBook.isbn13);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
