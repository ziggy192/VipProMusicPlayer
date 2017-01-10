package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nghia on 1/8/2017.
 */

public class MediaTypeHolder {
    @SerializedName("subgenres")
    private ArrayList<MusicGenre> genres;
    @SerializedName("id")
    private String id;

    public MediaTypeHolder(ArrayList<MusicGenre> genres, String id) {
        this.genres = genres;
        this.id = id;
    }

    public ArrayList<MusicGenre> getGenres() {
        return genres;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MediaTypeHolder{" +
                "genres=" + genres +
                '}';
    }
}
