package com.example.jhzhou.androidtxtbks;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/24/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private List<Book> mBookList;
    private Context mContext;
    private OnListItemClickedListener mListener;

    public interface OnListItemClickedListener {
        void onItemClicked(Book book);
    }

    public BookAdapter(Context context, List<Book> bookList, OnListItemClickedListener listener) {
        mContext = context;
        mBookList = bookList;
        mListener = listener;
    }

    public void setBookList(List<Book> bookList) {
        mBookList = bookList;
        notifyDataSetChanged();
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

        int price = (int)Math.floor(book.googlePrice * 100);

        holder.mainPriceTextView.setText("$" + String.valueOf(price / 100));
        holder.pennyPriceTextView.setText(String.valueOf(price % 100));

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

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.list_item_main_price) TextView mainPriceTextView;
        @BindView(R.id.list_item_penny_price) TextView pennyPriceTextView;
        @BindView(R.id.list_item_title) TextView bookTitleTextView;
        @BindView(R.id.list_item_author) TextView bookAuthorTextView;
        @BindView(R.id.list_item_bookcover) ImageView bookCoverImageView;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v("BookAdapter", "click");
            Book book = mBookList.get(getAdapterPosition());
            mListener.onItemClicked(book);
        }
    }
}
