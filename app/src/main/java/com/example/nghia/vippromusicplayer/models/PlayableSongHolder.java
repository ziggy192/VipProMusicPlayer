package com.example.nghia.vippromusicplayer.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Nghia on 1/15/2017.
 */

public class PlayableSongHolder {
    @SerializedName("docs")
    private PlayableSong[] songs;

    public PlayableSongHolder(PlayableSong[] songs) {
        this.songs = songs;
    }

    public PlayableSong[] getSongs() {
        return songs;
    }

    public PlayableSong getFirstSong() {
        if (songs.length > 0) {
            return songs[0];

        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "PlayableSongHolder{" +
                "songs=" + Arrays.toString(songs) +
                '}';
    }
}
