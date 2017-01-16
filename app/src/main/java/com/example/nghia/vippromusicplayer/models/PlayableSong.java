package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nghia on 1/15/2017.
 */

public class PlayableSong {
    @SerializedName("title")
    private String title;
    @SerializedName("artist")
    private String artist;
    @SerializedName("source")
    private SongUrl link;

    public PlayableSong(String title, String artist, SongUrl link) {
        this.title = title;
        this.artist = artist;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public SongUrl getLink() {
        return link;
    }
}
