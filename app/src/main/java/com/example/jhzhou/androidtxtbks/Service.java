package com.example.jhzhou.androidtxtbks;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bj on 17/04/2017.
 */

public class Service extends IntentService {

    public static final String BOOK_KEY = "BOOK_KEY";
    public static final String RECEIVER_KEY = "RECEIVER_KEY";

    public Service(){
        super("Service");
    }

    protected void onHandleIntent(Intent intent){
        RestMethods restMethods = new RestMethods(this);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER_KEY);
//        call google api
        try{
            ArrayList<Book> books = restMethods.getGoogleBooks("algorithm"); //<--replace with user's input
            Bundle data = new Bundle();
            data.putParcelableArrayList(BOOK_KEY, books);
            receiver.send(Activity.RESULT_OK, data);
        } catch(Exception e){
            Log.w("warning", e);
        }
    }
}
