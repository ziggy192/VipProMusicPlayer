package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

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
        return songs[0];
    }
}
