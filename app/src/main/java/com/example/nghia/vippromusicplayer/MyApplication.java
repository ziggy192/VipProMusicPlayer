package com.example.nghia.vippromusicplayer;

import android.app.Application;

import com.example.nghia.vippromusicplayer.utils.DBContext;

/**
 * Created by Nghia on 1/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBContext.init(this);
    }
}
