package com.example.nghia.vippromusicplayer.models.inner_models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Nghia on 1/12/2017.
 */

public class SongName extends RealmObject {

    @SerializedName("label")
    private String name;

    public SongName() {
    }

    public SongName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return
                "name='" + name ;
    }
}