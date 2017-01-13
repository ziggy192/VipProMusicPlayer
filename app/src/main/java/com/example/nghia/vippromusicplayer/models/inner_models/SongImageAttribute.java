package com.example.nghia.vippromusicplayer.models.inner_models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Nghia on 1/12/2017.
 */

public class SongImageAttribute extends RealmObject {
    static final String DEFAULT_SMALL_SIZE = "60";
    static final String DEFAULT_LARGE_SIZE = "170";
    @SerializedName("height")
    private String height;

    public SongImageAttribute() {
    }

    public SongImageAttribute(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }
}