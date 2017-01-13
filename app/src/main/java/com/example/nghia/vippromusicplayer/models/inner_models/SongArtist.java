package com.example.nghia.vippromusicplayer.models.inner_models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Nghia on 1/12/2017.
 */

public class SongArtist extends RealmObject {
    @SerializedName("label")
    private String name;

    public SongArtist() {
    }

    public SongArtist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name = " + name;
    }
}