package com.example.jhzhou.androidtxtbks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by bj on 17/04/2017.
 */

public class RestMethods {

    Context context;

    public RestMethods(Context context) {
        this.context = context;
    }

    public ArrayList<Book> getGoogleBooks(String bookTitle) throws SocketTimeoutException, MalformedURLException, IOException {
        if (!checkOnline()) {
            throw new SocketTimeoutException();
        }

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + bookTitle);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JsonReader jd = new JsonReader(new BufferedReader(new InputStreamReader(in)));
            return readBook((jd));
        } catch (Exception e) {
            Log.w("error", e);
        } finally {
            urlConnection.disconnect();
        }

        return new ArrayList<>();
    }

    private boolean checkOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private ArrayList<Book> readBook(JsonReader reader) throws IOException {
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book();
        String nextName = "";
        String nextString = "";

        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("items")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        nextName = reader.nextName();
                        if (nextName.equals("volumeInfo")) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                nextName = reader.nextName();
                                if (nextName.equals("title"))
                                    book.title = reader.nextString();
                                else if (nextName.equals("authors")) {
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        book.authors.add(reader.nextString());
                                    }
                                    reader.endArray();
                                } else if (nextName.equals("publisher")) {
                                    book.publisher = reader.nextString();
                                } else if (nextName.equals("publishedDate")) {
                                    book.publishDate = reader.nextString();
                                } else if (nextName.equals("description")) {
                                    book.description = reader.nextString();
                                } else if (nextName.equals("industryIdentifiers")) {
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        reader.beginObject();
                                        nextName = reader.nextName();
                                        nextString = reader.nextString();
                                        if (nextName.equals("type") && nextString.equals("ISBN_10")) {
                                            reader.nextName();
                                            book.isbn10 = reader.nextString();
                                        } else if (nextName.equals("type") && nextString.equals("ISBN_13")) {
                                            reader.nextName();
                                            book.isbn13 = reader.nextString();
                                        }
                                        reader.endObject();
                                    }
                                    reader.endArray();
                                } else if (nextName.equals("pageCount")) {
                                    book.pageCount = reader.nextInt();
                                } else if (nextName.equals("categories")) {
                                    reader.beginArray();
                                    while(reader.hasNext()){
                                        book.categories.add(reader.nextString());
                                    }
                                    reader.endArray();
                                } else if (nextName.equals("imageLinks")) {
                                    reader.beginObject();
                                    while (reader.hasNext()) {
                                        if (reader.nextName().equals("thumbnail"))
                                            book.imgLink = new URL(reader.nextString());
                                        else
                                            reader.skipValue();
                                    }
                                    reader.endObject();
                                } else
                                    reader.skipValue();

                            }
                            reader.endObject();
                        } else if (nextName.equals("saleInfo")) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                nextName = reader.nextName();
                                if (nextName.equals("isEbook")) {
                                    if (!reader.nextBoolean()) {
                                        book.googlePrice = 0;
                                    }
                                } else if (nextName.equals("retailPrice")) {
                                    reader.beginObject();
                                    while (reader.hasNext()) {
                                        if (reader.nextName().equals("amount"))
                                            book.googlePrice = reader.nextDouble();
                                        else
                                            reader.skipValue();
                                    }
                                    reader.endObject();
                                } else if (nextName.equals("buyLink"))
                                    book.googleLink = new URL(reader.nextString());
                                else
                                    reader.skipValue();
                            }
                            reader.endObject();
                        } else
                            reader.skipValue();
                    }
                    reader.endObject();
                    books.add(book);
                    book = new Book();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endArray();
        reader.endObject();

        return books;
    }
}
