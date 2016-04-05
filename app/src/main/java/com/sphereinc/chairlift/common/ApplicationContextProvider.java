package com.sphereinc.chairlift.common;

import android.app.Application;
import android.content.Context;

import com.sphereinc.chairlift.MainActivity;

public class ApplicationContextProvider extends Application {


    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

}