package com.sphereinc.chairlift.common.utils;

import android.content.Context;
import android.widget.Toast;

import com.sphereinc.chairlift.R;

import java.net.UnknownHostException;

public class ErrorHandler {

    public static void checkConnectionError(Context context, Throwable t) {
        if (t instanceof UnknownHostException) {
            Toast.makeText(context, context.getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }
}
