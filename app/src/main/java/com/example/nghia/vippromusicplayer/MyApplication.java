package com.example.nghia.vippromusicplayer;

import android.app.Application;

import com.example.nghia.vippromusicplayer.utils.DBContext;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;

/**
 * Created by Nghia on 1/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBContext.init(this);
        ServiceContext.init("https://rss.itunes.apple.com/data/"
                ,"https://itunes.apple.com/"
                ,"http://api.mp3.zing.vn/api/mobile/");

    }
}
