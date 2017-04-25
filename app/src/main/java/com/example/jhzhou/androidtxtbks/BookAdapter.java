package com.example.jhzhou.androidtxtbks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by jhzhou on 4/24/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private List<Book> mBookList;
    private Context mContext;

    public BookAdapter(Context context, List<Book> bookList) {
        mContext = context;
        mBookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = mBookList.get(position);
        // check
        holder.bookAuthorTextView.setText(book.authors.get(0));
        holder.bookTitleTextView.setText(book.title);

        double price = book.googlePrice;
        double penngPart = price - (int)price;
        holder.mainPriceTextView.setText(String.valueOf((int) price));
        holder.pennyPriceTextView.setText(String.valueOf(penngPart));

        // binding imageview
        Glide.with(mContext).load(book.imgLink).centerCrop().into(holder.bookCoverImageView);
    }

    @Override
    public int getItemCount() {
        if (mBookList == null || mBookList.size() == 0) {
            return 0;
        } else {
            return mBookList.size();
        }
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView mainPriceTextView;
        TextView pennyPriceTextView;
        TextView bookTitleTextView;
        TextView bookAuthorTextView;
        ImageView bookCoverImageView;

        public BookViewHolder(View itemView) {
            super(itemView);
            mainPriceTextView = (TextView) itemView.findViewById(R.id.list_item_main_price);
            pennyPriceTextView = (TextView) itemView.findViewById(R.id.list_item_penny_price);
            bookTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            bookAuthorTextView = (TextView) itemView.findViewById(R.id.list_item_author);
            bookCoverImageView = (ImageView) itemView.findViewById(R.id.list_item_bookcover);
        }
    }
}
