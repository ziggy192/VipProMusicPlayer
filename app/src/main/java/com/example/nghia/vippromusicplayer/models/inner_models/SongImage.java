package com.example.nghia.vippromusicplayer.models.inner_models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Nghia on 1/12/2017.
 */
public class SongImage  extends RealmObject {

    @SerializedName("label")
    private String url;
    @SerializedName("attributes")
    private SongImageAttribute attribute;

    public SongImage() {
    }

    public SongImage(String url, SongImageAttribute attribute) {
        this.url = url;
        this.attribute = attribute;
    }

    public String getUrl() {
        return url;
    }

    public SongImageAttribute getAttribute() {
        return attribute;
    }

    public boolean isSmall(){
        return attribute.getHeight().equals(SongImageAttribute.DEFAULT_SMALL_SIZE);
    }
    public boolean isLarge() {
        return attribute.getHeight().equals(SongImageAttribute.DEFAULT_LARGE_SIZE);
    }

    @Override
    public String toString() {
        return
                "url='" + url ;
    }


}
