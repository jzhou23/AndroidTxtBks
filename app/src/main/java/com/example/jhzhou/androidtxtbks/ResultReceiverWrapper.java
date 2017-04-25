package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by bj on 18/04/2017.
 */

public class ResultReceiverWrapper extends ResultReceiver {

    public static final String MESSAGE_SEQUENCE_NUMBER = "MESSAGE_SEQUENCE_NUMBER";

    public ResultReceiverWrapper(Handler handler) {
        super(handler);
    }

    public interface IReceive {
        public void onReceiveResult(int resultCode, Bundle data);
    }

    protected IReceive receiver;

    public void setReceiver(IReceive receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, data);
        }
    }
}