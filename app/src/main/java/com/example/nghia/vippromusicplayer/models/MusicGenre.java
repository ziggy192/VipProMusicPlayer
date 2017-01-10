package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nghia on 1/8/2017.
 */

public class MusicGenre implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("translation_key")
    private String translationKey;


    public MusicGenre(String id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    public String getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getDrawableName(){
        if (!id.isEmpty()) {
            return "genre" + id;
        } else {
            return "genre"+"0";
        }
    }

    @Override
    public String toString() {
        return "MusicGenre{" +
                "id='" + id + '\'' +
                ", translationKey='" + translationKey + '\'' +
                '}';
    }
}
