package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nghia on 1/10/2017.
 */

public class SongImage {

    @SerializedName("url")
    private String url;
    @SerializedName("attributes")
    private SongImageAttribute attribute;

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



    public class SongImageAttribute {
        private static final String DEFAULT_SMALL_SIZE = "60";
        private static final String DEFAULT_LARGE_SIZE = "170";
        private String height;

        public SongImageAttribute(String height) {
            this.height = height;
        }

        public String getHeight() {
            return height;
        }
    }
}
