package com.sphereinc.chairlift.common;

import android.content.Context;

import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

public class ImageHandler {

    private static Picasso instance;

    public static Picasso getSharedInstance(Context context) {
        if (instance == null) {
            instance = new Picasso.Builder(context).executor(Executors.newFixedThreadPool(10)).memoryCache(new LruCache(24000)).indicatorsEnabled(true).build();
        }
        return instance;
    }
}