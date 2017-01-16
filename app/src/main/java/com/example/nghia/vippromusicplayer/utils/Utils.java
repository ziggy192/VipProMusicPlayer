package com.example.nghia.vippromusicplayer.utils;

/**
 * Created by Nghia on 1/17/2017.
 */

public class Utils {
    public static double getProgressPercentage(int current, int total) {
        return ((double) current / total)*100;
    }
}
