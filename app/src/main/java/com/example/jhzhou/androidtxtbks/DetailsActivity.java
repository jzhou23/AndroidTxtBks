package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/29/17.
 */

public class DetailsActivity extends AppCompatActivity {

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
    @BindView(R.id.details_publishe_date_content) TextView publishDateTextView;
    @BindView(R.id.details_description_content) TextView descriptionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBook = getIntent().getParcelableExtra(BOOK);

        int price = (int)Math.floor(mBook.googlePrice * 100);

        mainPriceTextView.setText("$" + String.valueOf(price / 100));
        pennyPriceTextView.setText(String.valueOf(price % 100));

        titleTextView.setText(mBook.title);
        //check
        authorTextView.setText(mBook.authors.get(0));
        Glide.with(this).load(mBook.imgLink).centerCrop().into(coverImageView);

        isbn10TextView.setText(mBook.isbn10);
        isbn13TextView.setText(mBook.isbn13);

        subjectTextView.setText(mBook.categories.get(0));  //<-- use first category just for now
        handCoverTextView.setText(String.valueOf(mBook.pageCount));
        publisherTextView.setText(mBook.publisher);
        publishDateTextView.setText(mBook.publishDate);
        descriptionTextView.setText(mBook.description);
    }
}
