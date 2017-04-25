package com.example.jhzhou.androidtxtbks;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bj on 17/04/2017.
 */

public class Book implements Parcelable {

    public String title;
    public List<String> authors = new LinkedList<>();
    public double googlePrice;
    public double amazonPrice;
    public URL googleLink;
    public URL amazonLink;
    public String isbn10;
    public String isbn13;
    public URL imgLink;
    public String publisher;
    public int pageCount;
    public String description;
    public List<String> categories = new LinkedList<>();
    public String publishDate;

    public Book(){
    }

    public Book(Parcel in) {
        this.title = in.readString();
        in.readList(authors, List.class.getClassLoader());
        this.googlePrice = in.readDouble();
        this.amazonPrice = in.readDouble();
        try{
            this.googleLink = new URL(in.readString());
            this.amazonLink = new URL(in.readString());
        }
        catch(Exception e){
            Log.e("error", e.getMessage());
        }
        this.isbn10 = in.readString();
        this.isbn13 = in.readString();
        try{
            this.imgLink = new URL(in.readString());
        }
        catch(Exception e){
            Log.e("error", e.getMessage());
        }
        this.publisher = in.readString();
        this.pageCount = in.readInt();
        this.description = in.readString();
        in.readList(this.categories, List.class.getClassLoader());
        this.publishDate = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.title);
        out.writeList(this.authors);
        out.writeDouble(this.googlePrice);
        out.writeDouble(this.amazonPrice);
        out.writeString(this.googleLink.toString());
        out.writeString(this.amazonLink.toString());
        out.writeString(this.isbn10);
        out.writeString(this.isbn13);
        out.writeString(this.imgLink.toString());
        out.writeString(this.publisher);
        out.writeInt(this.pageCount);
        out.writeString(this.description);
        out.writeList(this.categories);
        out.writeString(this.publishDate);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
