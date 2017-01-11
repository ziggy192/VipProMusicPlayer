package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Nghia on 1/10/2017.
 */

public class SongsDetail {
    @SerializedName("im:name")
    private SongName songName;
    @SerializedName("im:image")
    private SongImage[] songImages;
    @SerializedName("im:artist")
    private SongArtist songArtist;

    public SongsDetail(SongName songName, SongImage[] songImages, SongArtist songArtist) {
        this.songName = songName;
        this.songImages = songImages;
        this.songArtist = songArtist;
    }

    public String getSongName() {
        return songName.getName();
    }

    public SongImage[] getSongImages() {
        return songImages;
    }


    public String getSongArtist() {
        return songArtist.getName();
    }

    public String getSmallImageUrl(){
        String url;
        for (SongImage image : songImages) {
            if (image.isSmall()) {
                return image.getUrl();
            }
        }
        return null;
    }

    public String getLargeImageUrl(){
        String url;
        for (SongImage image : songImages) {
            if (image.isLarge()) {
                return image.getUrl();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SongsDetail{" +
                "songName=" + songName +
                ", songImages=" + Arrays.toString(songImages) +
                ", songArtist=" + songArtist +
                '}';
    }

    public class SongName{

        @SerializedName("label")
        private String name;

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
    public class SongArtist{
        @SerializedName("label")
        private String name;

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
}
