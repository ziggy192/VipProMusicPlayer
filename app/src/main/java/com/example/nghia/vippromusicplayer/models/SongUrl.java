package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nghia on 1/15/2017.
 */

public class SongUrl {
    @SerializedName("128")
    private String link128;
    @SerializedName("320")
    private String link320;
    @SerializedName("lossless")
    private String linkLossless;

    public SongUrl(String link128, String link320, String linkLossless) {
        this.link128 = link128;
        this.link320 = link320;
        this.linkLossless = linkLossless;
    }

    public String getLink128() {
        return link128;
    }

    public String getLink320() {
        return link320;
    }

    public String getLinkLossless() {
        return linkLossless;
    }
}
