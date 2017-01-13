package com.example.nghia.vippromusicplayer.models;

import com.example.nghia.vippromusicplayer.utils.DBContext;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nghia on 1/8/2017.
 */

public class MusicGenre extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private String id;
    @SerializedName("translation_key")
    private String translationKey;

    private boolean isFavorite;

    public MusicGenre() {
    }

    public MusicGenre(String id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
        isFavorite = false;
    }


    public void setFavorite(boolean favorite) {

        isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
    public void changeFavorite(){
        isFavorite = !isFavorite;
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
